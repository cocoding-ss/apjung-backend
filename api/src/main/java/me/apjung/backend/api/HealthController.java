package me.apjung.backend.api;

import me.apjung.backend.property.MyProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @Autowired
    private MyProfile myProfile;

    @GetMapping("/health")
    public String test() {
        return "v15 " + myProfile.getDefaultProfile() + " " + myProfile.getMyProfile();
    }
}
