package app.store.controller;

import app.store.dto.request.auth.AuthenticationRequest;
import app.store.dto.request.auth.IntrospectRequest;
import app.store.dto.request.auth.LogoutRequest;
import app.store.dto.request.auth.RefreshRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.auth.AuthenticationResponse;
import app.store.dto.response.auth.IntrospectResponse;
import app.store.dto.response.auth.RefreshResponse;
import app.store.service.implementation.AuthenticationServiceImpl;
import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationServiceImpl authenticationServiceImpl;
    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var result = authenticationServiceImpl.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .build();

    }

    @PostMapping("/refresh")
    ApiResponse<RefreshResponse> re(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var result = authenticationServiceImpl.refreshToken(request);
        return ApiResponse.<RefreshResponse>builder()
                .result(result)
                .build();

    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

        var result = authenticationServiceImpl.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .build();

    }


    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {

        authenticationServiceImpl.logout(request);
        return ApiResponse.<Void>builder()
                .build();

    }
}
