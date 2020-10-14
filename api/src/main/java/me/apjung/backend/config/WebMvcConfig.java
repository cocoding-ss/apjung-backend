package me.apjung.backend.config;

import me.apjung.backend.component.YamlMessageSource.YamlMessageSource;
import net.rakugakibox.util.YamlResourceBundle;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.resource.PathResourceResolver;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**********************
     * 정적 리소스 설정
     * RestDocs의 html 파일들이 Static Resource로 들어가지기 때문에 필요함
     *********************/
    private static final String [] RESOURCE_LOCATIONS = {
            "classpath:/static/"
    };

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/**")
                .addResourceLocations(RESOURCE_LOCATIONS)
                .setCachePeriod(3600)
                .resourceChain(true)
                .addResolver(new PathResourceResolver());
    }

    /**********************
     * CORS 설정
     * 모두 열어놓은 상태
     *********************/
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600);
    }

    /**********************
     * MessageSource와 Locale 설정
     * /locael/validation 유효성 검사와 익셉션에서 사용하는 메시지 소스
     * /locale/business 비즈니스 로직에서 사용하는 메시지 소스
     *********************/
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(Locale.KOREA);
        return localeResolver;
    }

//    @Bean("messageSource")
//    public MessageSource messageSource() {
//        YamlMessageSource messageSource = new YamlMessageSource();
//        messageSource.setBasename("locale/validation/message");
//        messageSource.setDefaultEncoding("UTF-8");
////        messageSource.setAlwaysUseMessageFormat(true);
////        messageSource.setUseCodeAsDefaultMessage(true);
////        messageSource.setFallbackToSystemLocale(true);
//        return messageSource;
//    }

    @Bean
    public MessageSource validationMessageSource() {
        YamlMessageSource messageSource = new YamlMessageSource();
        messageSource.setBasename("locale/validation/message");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setAlwaysUseMessageFormat(true);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(true);
        return messageSource;
    }

    @Bean
    public MessageSource businessMessageSource() {
        YamlMessageSource messageSource = new YamlMessageSource();
        messageSource.setBasename("locale/business/message");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setAlwaysUseMessageFormat(true);
        messageSource.setUseCodeAsDefaultMessage(true);
        messageSource.setFallbackToSystemLocale(true);
        return messageSource;
    }

    /**********************
     * Srping Boot Validation 커스텀 설정
     * 에러 메시지로 MessageSource를 사용하기 위해 작성
     *********************/
    @Override
    public Validator getValidator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(validationMessageSource());
        return bean;
    }
}
