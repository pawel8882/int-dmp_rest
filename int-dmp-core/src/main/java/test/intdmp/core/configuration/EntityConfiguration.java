package test.intdmp.core.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan("test.intdmp.core.model")
public class EntityConfiguration {
}
