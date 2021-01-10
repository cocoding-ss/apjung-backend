package me.apjung.backend.property;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties("jwt")
public class JwtProps {
    private TokenProps accessTokenProps = new AccessTokenProps();
    private TokenProps refreshTokenProps = new RefreshTokenProps();

    @Getter
    @Setter
    @ToString
    @Configuration
    @ConfigurationProperties("access-token")
    public static class AccessTokenProps implements TokenProps {
        private String secret;
        private Long expirationTimeMilliSec;
    }

    @Getter
    @Setter
    @Configuration
    @ConfigurationProperties("refresh-token")
    public static class RefreshTokenProps implements TokenProps {
        private String secret;
        private Long expirationTimeMilliSec;
    }

    public interface TokenProps {
        String getSecret();
        Long getExpirationTimeMilliSec();
    }
}
