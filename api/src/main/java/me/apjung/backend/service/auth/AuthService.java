package me.apjung.backend.service.auth;

import me.apjung.backend.domain.user.User;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.dto.response.AuthResponse;
import me.apjung.backend.service.security.CustomUserDetails;

public interface AuthService {
    User register(AuthRequest.Register request);
    User emailVerify(Long userId, String emailAuthToken);

    AuthResponse.Login jwtLogin(AuthRequest.Login request);
    AuthResponse.Me me(CustomUserDetails customUserDetails);
}
