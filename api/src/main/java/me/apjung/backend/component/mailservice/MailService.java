package me.apjung.backend.component.mailservice;

import me.apjung.backend.domain.user.User;

public interface MailService {
    void sendEmailAuth(User user);
}
