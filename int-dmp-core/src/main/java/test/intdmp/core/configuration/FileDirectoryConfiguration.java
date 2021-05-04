package test.intdmp.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileDirectoryConfiguration {

    private final String resource_dir = "F:\\Projects";

    @Bean
    public String saveDirectory() {
        return resource_dir;
    }
}
