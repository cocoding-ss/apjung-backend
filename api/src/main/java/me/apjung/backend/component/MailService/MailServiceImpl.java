package me.apjung.backend.component.MailService;

import me.apjung.backend.component.CustomMessageSourceResolver.CustomMessageSourceResolver;
import me.apjung.backend.domain.User.User;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
public class MailServiceImpl implements MailService {
    private final MailHandler mailHandler;
    private final CustomMessageSourceResolver customMessageSourceResolver;
    private final ServletContext servletContext;

    public MailServiceImpl(MailHandler mailHandler, CustomMessageSourceResolver customMessageSourceResolver, ServletContext servletContext) {
        this.mailHandler = mailHandler;
        this.customMessageSourceResolver = customMessageSourceResolver;
        this.servletContext = servletContext;
    }

    public void sendEmailAuth(User user) {
        WebContext context = getDefaultContext();

        context.setVariable("userId", user.getId());
        context.setVariable("emailAuthToken", user.getEmailAuthToken());
        String mailContent = mailHandler.getTemplateHtml("auth/email_verify", context);

        CustomMailMessage customMailMessage = CustomMailMessage.builder()
                .to(user.getEmail())
                .subject(customMessageSourceResolver.getBusinessMessage("template.email.auth.email_verify.title"))
                .text(mailContent)
                .isHtml(true)
                .build();

        mailHandler.send(customMailMessage);
    }

    private WebContext getDefaultContext() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();

        WebContext webContext = new WebContext(
                request,
                response,
                servletContext,
                LocaleContextHolder.getLocale()
        );

        webContext.setVariable("baseUrl", request.getScheme() + "://" + request.getServerName());
        return webContext;
    }
}
