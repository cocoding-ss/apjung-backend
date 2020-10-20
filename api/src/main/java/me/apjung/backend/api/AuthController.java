package me.apjung.backend.api;

import me.apjung.backend.domain.User.User;
import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.dto.response.AuthResponse;
import me.apjung.backend.repository.User.UserRepository;
import me.apjung.backend.service.Auth.AuthService;
import me.apjung.backend.service.Security.CurrentUser;
import me.apjung.backend.service.Security.CustomUserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@Valid @RequestBody AuthRequest.Register request) {
        authService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse.Login login(@RequestBody AuthRequest.Login request) {
        return authService.jwtLogin(request);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse.Me me(@CurrentUser CustomUserDetails customUserDetails) {
        return authService.me(customUserDetails);
    }
}
