package me.apjung.backend.property.AppProps;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Configuration
@ConfigurationProperties("app")
public class AppProps {
    private AppEnv currentEnv;

    private List<String> devEmails = new ArrayList<>();
}
