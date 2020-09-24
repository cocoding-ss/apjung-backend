package me.apjung.backend.property;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
public class MyProfile {
    @Value("${defaultprofile}")
    private String defaultProfile;

    @Value("${myprofile}")
    private String myProfile;
}
