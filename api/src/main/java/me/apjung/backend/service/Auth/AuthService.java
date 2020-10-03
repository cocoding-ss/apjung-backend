package me.apjung.backend.service.Auth;

import me.apjung.backend.dto.request.AuthRequest;

public interface AuthService {
    public void register(AuthRequest.Register request);
}
