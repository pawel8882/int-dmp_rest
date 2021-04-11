package test.intdmp.core.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.projects.SectionDepartments;
import test.intdmp.core.service.ProjectService;
import test.intdmp.core.service.repository.PersonRepository;
import test.intdmp.core.service.repository.ProjectRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CollectProjects {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private PersonRepository personRepository;

    public Project getOneProject(Integer projectId) {
        return projectRepository.findById(projectId).get();
    }

    public List<Project> getAllProjects() {

        return projectRepository.findAll();
    }

    public List<Project> getUserProjects(String user) {

        List<Project> projects = new ArrayList<>();
        Person person = personRepository.findByUsername(user);
        Set<PersonsProjects> personProjects = person.getProjects();
        for (PersonsProjects p: personProjects)
        {projects.add(p.getProject()); }
        projects.sort(Comparator.comparing(Project::getNumber));
        return projects;
    }

    public List<SectionDepartments> userDepartments(Integer projectId, String user) {

        Set<SectionDepartments> sectionDepartments = personRepository.findByUsername(user).getSectionDepartments()
                .stream().filter(e -> e.getProject().getId().equals(projectId)).collect(Collectors.toSet());
        List<SectionDepartments> sectionDepartmentsAlphabetical = new ArrayList<>(sectionDepartments);
        sectionDepartmentsAlphabetical.sort(Comparator.comparing(SectionDepartments::getSection));

        return sectionDepartmentsAlphabetical;

    }

}
