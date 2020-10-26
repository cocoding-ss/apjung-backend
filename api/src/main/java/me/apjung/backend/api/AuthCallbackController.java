package me.apjung.backend.api;

import me.apjung.backend.service.Auth.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/auth/callback")
public class AuthCallbackController {
    private final AuthService authService;

    public AuthCallbackController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/email/verify")
    public ModelAndView emailAuth(@RequestParam Long userId, @RequestParam String emailAuthToken) {
        ModelAndView modelAndView = new ModelAndView("/templates/callback/auth/email_verify");

        modelAndView.addObject("user", authService.emailVerify(userId, emailAuthToken));

        return modelAndView;
    }
}
