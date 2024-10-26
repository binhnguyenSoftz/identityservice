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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Component
public class JwtService {
    @Value("${jwt.key}")
    private String SECRET;

    @Value("${jwt.issuer}")
    private String ISSUER;

    @Value("${jwt.ttl}")
    private Long TTL;

    public String generateToken(User user) {
        try {
            JWSHeader jwsHeader = new JWSHeader
                    .Builder(JWSAlgorithm.HS512)
                    .build();
            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                    .subject(user.getUsername())
                    .issuer(this.ISSUER)
                    .expirationTime(new Date(Instant.now().plus(this.TTL, ChronoUnit.SECONDS).toEpochMilli()))
                    .issueTime(new Date())
                    .claim("userId", user.getId())
                    .build();

            JWSSigner signer = new MACSigner(SECRET.getBytes());
            SignedJWT signedJWT = new SignedJWT(jwsHeader, claimsSet);
            signedJWT.sign(signer);
            return signedJWT.serialize();
        } catch (Exception e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }

    public boolean verifyToken(String token) {
        try {
            SignedJWT signedJWT = SignedJWT.parse(token);
            JWSVerifier verifier = new MACVerifier(SECRET);

            if (!signedJWT.verify(verifier)) {
                return false;
            }

            JWTClaimsSet claims = signedJWT.getJWTClaimsSet();
            if (claims.getExpirationTime() == null || new Date().after(claims.getExpirationTime())) {
                return false;
            }

            String issuer = claims.getIssuer();
            if (issuer == null || !issuer.equals(this.ISSUER)) {
                return false;
            }

            return true;
        } catch (ParseException | JOSEException e) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
    }
}
