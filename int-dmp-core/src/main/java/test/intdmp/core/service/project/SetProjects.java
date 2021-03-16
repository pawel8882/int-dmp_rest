package test.intdmp.core.service.project;

import org.springframework.stereotype.Service;
import test.intdmp.core.model.projects.Project;

import javax.persistence.EntityManager;

@Service
public class SetProjects {

    private EntityManager entityManager;

    public SetProjects(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Integer createProject(Project project) {
        project.getDetails().forEach(projectDetails -> projectDetails.setProject(project));
        entityManager.persist(project);
        return project.getId();
    }

}
