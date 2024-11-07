package com.softz.identity.utils;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jose.*;
import com.softz.identity.entity.User;
import com.softz.identity.exception.AppException;
import com.softz.identity.exception.ErrorCode;
import com.softz.identity.repository.InvalidatedTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.StringJoiner;
import java.util.UUID;

@Component
public class JwtService {
    @Value("${jwt.key}")
    private String SECRET;

    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.ttl}")
    private Long TTL;

    @Autowired
    InvalidatedTokenRepository invalidatedTokenRepository;

    public String generateToken(User user) {
        try {
            JWSHeader jwsHeader = new JWSHeader
                    .Builder(JWSAlgorithm.HS512)
                    .build();
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer(this.ISSUER)
                    .claim("userId", user.getId())
                    .expirationTime(new Date(Instant.now().plus(this.TTL, ChronoUnit.SECONDS).toEpochMilli()))
                    .issueTime(new Date())
                    .claim("scope", buildScope(user))
                    .jwtID(UUID.randomUUID().toString())
                    .build();

            JWSSigner signer = new MACSigner(SECRET.getBytes());
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public TokenInfo extractToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            if (SECRET == null) {
                throw new RuntimeException("Secret key is not initialized");
            }
            JWSVerifier jwsVerifier = new MACVerifier(SECRET.getBytes());

            // Check token expiration
            Date expirationTime = signedJWT.getJWTClaimsSet().getExpirationTime();
            if (expirationTime == null || expirationTime.before(new Date())) {
                return null;
            }

            // Verify signature
            if (!signedJWT.verify(jwsVerifier)) {
                return null;
            }

            // Return TokenInfo object
            return new TokenInfo(signedJWT.getJWTClaimsSet().getJWTID(), signedJWT.getJWTClaimsSet().getExpirationTime().toInstant());
        } catch (ParseException | JOSEException e) {
            throw new RuntimeException("Error parsing or verifying the token", e);
        }
    }

    public record TokenInfo(String jti, Instant expiryTime) { }

    public SignedJWT introspectToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);

            JWSVerifier jwsVerifier = new MACVerifier(SECRET.getBytes());

            if (signedJWT.getJWTClaimsSet()
                    .getExpirationTime().before(new Date())) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            String jti = signedJWT.getJWTClaimsSet().getJWTID();
            if (invalidatedTokenRepository.existsById(jti)) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            if (!signedJWT.verify(jwsVerifier)) {
                throw new AppException(ErrorCode.UNAUTHENTICATED);
            }

            return signedJWT;
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    private String buildScope(User user) {
        if (CollectionUtils.isEmpty(user.getRoles())) {
            return "";
        }

        StringJoiner stringJoiner = new StringJoiner(" ");

        user.getRoles().forEach((role) -> {
            stringJoiner.add(String.format("ROLE_%s", role.getName()));
            if (!CollectionUtils.isEmpty(role.getPermissions())) {
                role.getPermissions()
                        .forEach((permission) -> stringJoiner.add(permission.getName()));
            }
        });

        return stringJoiner.toString();
    }
}
