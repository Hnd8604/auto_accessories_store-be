package app.store.service.interfaces;

import app.store.dto.request.auth.AuthenticationRequest;
import app.store.dto.request.auth.IntrospectRequest;
import app.store.dto.request.auth.LogoutRequest;
import app.store.dto.request.auth.RefreshRequest;
import app.store.dto.response.auth.AuthenticationResponse;
import app.store.dto.response.auth.IntrospectResponse;
import app.store.dto.response.auth.RefreshResponse;
import app.store.entity.User;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jwt.SignedJWT;
import jakarta.servlet.http.HttpSession;

import java.text.ParseException;

public interface AuthenticationService {
    IntrospectResponse introspect(IntrospectRequest request) throws JOSEException, ParseException;

    AuthenticationResponse authenticate(AuthenticationRequest request, HttpSession session);

    void logout(LogoutRequest request) throws ParseException, JOSEException;

    void invalidateToken(String token, String type)throws ParseException, JOSEException;

    RefreshResponse refreshToken(RefreshRequest request) throws ParseException, JOSEException;







}
