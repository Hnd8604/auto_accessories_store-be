package app.store.controller;

import app.store.constant.ResponseMessage;
import app.store.dto.request.auth.AuthenticationRequest;
import app.store.dto.request.auth.IntrospectRequest;
import app.store.dto.request.auth.LogoutRequest;
import app.store.dto.request.auth.RefreshRequest;
import app.store.dto.response.auth.ApiResponse;
import app.store.dto.response.auth.AuthenticationResponse;
import app.store.dto.response.auth.IntrospectResponse;
import app.store.dto.response.auth.RefreshResponse;
import app.store.service.impl.AuthenticationServiceImpl;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
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
@Tag(name = "Authentication", description = "APIs for user authentication including login, token refresh, and logout")
public class AuthenticationController {
    AuthenticationServiceImpl authenticationServiceImpl;
    
    @PostMapping("/token")
    @Operation(
        summary = "Authenticate user",
        description = "Authenticates user with username and password. Returns JWT access token and refresh token."
    )
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request, HttpSession session) {
        var result = authenticationServiceImpl.authenticate(request, session);

        return ApiResponse.<AuthenticationResponse>builder()
                .result(result)
                .message(ResponseMessage.AUTHENTICATE_SUCCESS)
                .build();

    }

    @PostMapping("/refresh")
    @Operation(
        summary = "Refresh token",
        description = "Refreshes the access token using a valid refresh token. Returns new access token."
    )
    ApiResponse<RefreshResponse> refresh(@RequestBody RefreshRequest request) throws ParseException, JOSEException {
        var result = authenticationServiceImpl.refreshToken(request);
        return ApiResponse.<RefreshResponse>builder()
                .result(result)
                .message(ResponseMessage.AUTHENTICATE_SUCCESS)
                .build();

    }

    @PostMapping("/introspect")
    @Operation(
        summary = "Introspect token",
        description = "Validates a JWT token and returns its status. Used to check if a token is valid and not expired."
    )
    ApiResponse<IntrospectResponse> introspect(@RequestBody IntrospectRequest request)
            throws ParseException, JOSEException {

        var result = authenticationServiceImpl.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result)
                .message(ResponseMessage.INTROSPECT_SUCCESS)
                .build();

    }


    @PostMapping("/logout")
    @Operation(
        summary = "Logout user",
        description = "Logs out user by invalidating the JWT token. Adds token to blacklist to prevent reuse."
    )
    ApiResponse<Void> logout(@RequestBody LogoutRequest request)
            throws ParseException, JOSEException {

        authenticationServiceImpl.logout(request);
        return ApiResponse.<Void>builder()
                .message(ResponseMessage.LOGOUT_SUCCESS)
                .build();

    }
}
