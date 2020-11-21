package me.apjung.backend.property;

import lombok.Getter;
import lombok.Setter;
import me.apjung.backend.component.YamlProperty.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties("security")
@PropertySource(value = {"classpath:/security.yaml"}, ignoreResourceNotFound = true, factory = YamlPropertySourceFactory.class)
public class SecurityProps {
    List<String> permittedEndpoints;
}
