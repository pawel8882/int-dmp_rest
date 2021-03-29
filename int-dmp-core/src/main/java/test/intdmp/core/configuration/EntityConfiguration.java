package test.intdmp.core.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import test.intdmp.core.model.projects.Project;

@Configuration
@EntityScan("test.intdmp.core.model")
public class EntityConfiguration {

}
