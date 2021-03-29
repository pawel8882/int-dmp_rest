package test.intdmp.core.service.project;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.projects.SectionDepartments;
import test.intdmp.core.service.ProjectService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GetProjects {

    private EntityManager entityManager;

    public GetProjects(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Project getOneProject(Integer projectId) {
        return entityManager.find(Project.class, projectId);
    }

    public List<Project> getAllProjects() {

        return entityManager.createQuery("SELECT a FROM Project a", Project.class).getResultList();
    }

    public List<Project> getUserProjects(String user) {

        List<Project> projects = new ArrayList<>();
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        Set<PersonsProjects> PreProject = person.getProjects();
        for (PersonsProjects p: PreProject)
        {projects.add(p.getProject()); }
        return projects;
    }

    public List<SectionDepartments> userDepartments(Integer projectId, String user) {

        Set<SectionDepartments> sectionDepartments = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult().getSectionDepartments()
                .stream().filter(e -> e.getProject().getId().equals(projectId)).collect(Collectors.toSet());
        List<SectionDepartments> sectionDepartmentsAlphabetical = new ArrayList<>(sectionDepartments);
        sectionDepartmentsAlphabetical.sort(Comparator.comparing(SectionDepartments::getSection));

        return sectionDepartmentsAlphabetical;

    }

}
