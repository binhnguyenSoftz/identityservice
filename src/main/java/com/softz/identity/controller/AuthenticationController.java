package com.softz.identity.controller;

import com.softz.identity.dto.AccessTokenDto;
import com.softz.identity.dto.ApiResponse;
import com.softz.identity.dto.request.AuthenticationRequest;
import com.softz.identity.dto.request.InvalidatedTokenRequest;
import com.softz.identity.service.coordinator.AuthenticationCoordinatorService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
class AuthenticationController {
    private AuthenticationCoordinatorService authenticationCoordinatorService;

    @PostMapping("/auth/token")
    public ApiResponse<AccessTokenDto> authenticate(@RequestBody @Valid AuthenticationRequest request) {
        AccessTokenDto accessToken = authenticationCoordinatorService.authenticate(request);
        return ApiResponse.<AccessTokenDto>builder().result(accessToken).build();
    }

    @PostMapping("/auth/logout")
    public ApiResponse<Void> logout(@RequestBody @Valid InvalidatedTokenRequest request) throws ParseException {
        authenticationCoordinatorService.invalidateToken(request);
        return ApiResponse.<Void>builder().build();
    }
}