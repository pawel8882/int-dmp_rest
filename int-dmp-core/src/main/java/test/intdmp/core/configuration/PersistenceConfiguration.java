package test.intdmp.core.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import test.intdmp.core.model.person.messages.DataMessages;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.service.repository.*;

@Configuration
@EnableJpaRepositories(basePackageClasses = {PersonRepository.class, Project.class, DataMessages.class, CategoriesMessagesRepository.class})
public class PersistenceConfiguration {
}
