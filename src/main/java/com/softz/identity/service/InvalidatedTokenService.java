package com.softz.identity.service;

import com.softz.identity.entity.InvalidatedToken;
import com.softz.identity.repository.InvalidatedTokenRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class InvalidatedTokenService {
    @Autowired
    private InvalidatedTokenRepository invalidatedTokenRepository;

    public void createInvalidatedToken(InvalidatedToken token) {
        invalidatedTokenRepository.save(token);
    }
}
