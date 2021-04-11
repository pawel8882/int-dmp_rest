package test.intdmp.core.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.service.repository.*;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Date;

@Component
public class AddProjects {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private PersonsProjectsRepository personsProjectsRepository;

    public Integer createProject(Project project, String user) {
        Person person = personRepository.findByUsername(user);
        project.getDetails().forEach(projectDetails -> projectDetails.setProject(project));
        projectRepository.save(project);

        createPersonsProject(project, person);

        return project.getId();
    }

    private void createPersonsProject(Project project, Person person){

        Date date = new Date();
        PersonsProjects personProjects = new PersonsProjects();
        personProjects.setProject(project);
        personProjects.setPerson(person);
        personProjects.setAddingDate(new Timestamp(date.getTime()));

        personsProjectsRepository.save(personProjects);

    }

}
