package me.apjung.backend.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("jwt")
public class JwtProps {
    private AccessToken accessToken = new AccessToken();
    private RefreshToken refreshToken = new RefreshToken();

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties("access-token")
    public static class AccessToken {
        private String secret;
        private Long expirationTimeMilliSec;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties("refresh-token")
    public static class RefreshToken {
        private String secret;
        private Long expirationTimeMilliSec;
    }
}
