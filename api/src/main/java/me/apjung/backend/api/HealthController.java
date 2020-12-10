package me.apjung.backend.api;

import me.apjung.backend.property.appprops.AppProps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    @Autowired
    private AppProps appProps;

    @GetMapping("/health")
    public String test() {
        return "v15 currentEnv :: " + appProps.getCurrentEnv();
    }
}
