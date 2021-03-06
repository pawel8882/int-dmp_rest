package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.model.projects.SectionDepartments;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    private EntityManager entityManager;

    public ProjectService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Project> getProjectList(String user) {
        return getUserProjects(user);
    }

    public Project getProject(Integer projectId) { return entityManager.find(Project.class, projectId); }

    public List<SectionDepartments> getDepartmentsForProject(Integer projectId, String user) { return userDepartments(projectId, user); }

    public Integer createProject(Project project) {
        project.getDetails().forEach(projectDetails -> projectDetails.setProject(project));
        entityManager.persist(project);
        return project.getId();
    }

    public void updateProject(Integer projectId, Project project) {

        Project toUpdate = entityManager.find(Project.class, projectId);
        toUpdate.setName(project.getName());
        toUpdate.setComplete(project.isComplete());
        toUpdate.setNumber(project.getNumber());
        entityManager.merge(toUpdate);


    }

    public void removeProject(Integer projectId) {

        entityManager.remove(entityManager.find(Project.class, projectId));

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
