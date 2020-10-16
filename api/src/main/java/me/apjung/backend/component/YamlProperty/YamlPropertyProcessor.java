package me.apjung.backend.component.YamlProperty;


import org.springframework.beans.factory.config.YamlProcessor;
import org.springframework.core.CollectionFactory;
import org.springframework.core.io.Resource;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class YamlPropertyProcessor extends YamlProcessor {
    YamlPropertyProcessor(Resource resource) throws IOException {
        if (!resource.exists()) {
            throw new FileNotFoundException();
        }
        this.setResources(resource);
    }

    public Properties createProperties() throws IOException {
        final Properties results = CollectionFactory.createStringAdaptingProperties();
        process(((properties, map) -> results.putAll(properties)));
        return results;
    }
}
