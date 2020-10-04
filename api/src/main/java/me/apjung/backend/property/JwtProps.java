package me.apjung.backend.property;

import lombok.Getter;
import lombok.Setter;
import me.apjung.backend.component.YamlProperty.YamlPropertySourceFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@ConfigurationProperties("jwt")
@PropertySource(value = {"classpath:/jwt.yaml"}, ignoreResourceNotFound = true, factory = YamlPropertySourceFactory.class)
public class JwtProps {
    String secret;
    Integer expirationTimeSec;
}
