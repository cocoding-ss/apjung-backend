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
    private AccessTokenProps accessTokenProps = new AccessTokenProps();
    private RefreshTokenProps refreshTokenProps = new RefreshTokenProps();

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties("access-token-props")
    public static class AccessTokenProps implements TokenProps {
        private String secret;
        private Long expirationTimeMilliSec;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties("refresh-token-props")
    public static class RefreshTokenProps implements TokenProps {
        private String secret;
        private Long expirationTimeMilliSec;
    }

    public interface TokenProps {
        String getSecret();
        Long getExpirationTimeMilliSec();
    }
}
