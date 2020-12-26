package me.apjung.backend.property;

import lombok.Getter;
import lombok.Setter;
import me.apjung.backend.component.yamlproperty.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Setter
@Getter
@Configuration
@ConfigurationProperties("storage")
//@PropertySource(value = {"classpath:/storage.yaml"}, ignoreResourceNotFound = true, factory = YamlPropertySourceFactory.class)
public class StorageProps {
    String s3Bucket;
    String s3Public;
}
