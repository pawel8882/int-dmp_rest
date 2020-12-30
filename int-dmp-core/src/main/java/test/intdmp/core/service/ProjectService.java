package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.Project;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

@Service
@Transactional
public class ProjectService {

    private EntityManager entityManager;

    public ProjectService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Project> getProjectList() {
        return getAllProjects();
    }

    public Project getProject(Integer projectId) { return DetailedProject(projectId); }

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

    public Project DetailedProject(Integer projectId) {

        Project project = entityManager.find(Project.class, projectId);

        return project;

    }

}
