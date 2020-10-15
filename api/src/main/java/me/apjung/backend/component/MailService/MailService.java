package me.apjung.backend.component.MailService;

import me.apjung.backend.domain.User.User;

public interface MailService {
    void sendEmailAuth(User user);
}
