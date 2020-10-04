package me.apjung.backend.service.Auth;

import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.dto.response.AuthResponse;

public interface AuthService {
    void register(AuthRequest.Register request);
    AuthResponse.Login jwtLogin(AuthRequest.Login request);
}
