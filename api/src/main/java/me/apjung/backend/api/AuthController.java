package me.apjung.backend.api;

import me.apjung.backend.dto.request.AuthRequest;
import me.apjung.backend.dto.response.AuthResponse;
import me.apjung.backend.service.auth.AuthService;
import me.apjung.backend.service.security.CurrentUser;
import me.apjung.backend.service.security.CustomUserDetails;
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

    @PostMapping("/token")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse.TokenIssuance reissueAccessToken(@RequestBody AuthRequest.TokenIssuance request) {
        return authService.reissueAccessToken(request);
    }

    @GetMapping("/me")
    @ResponseStatus(HttpStatus.OK)
    public AuthResponse.Me me(@CurrentUser CustomUserDetails customUserDetails) {
        return authService.me(customUserDetails);
    }
}
