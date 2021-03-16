package test.intdmp.core.service.project;

import org.springframework.stereotype.Service;
import test.intdmp.core.model.projects.Project;

import javax.persistence.EntityManager;

@Service
public class ModifyProjects {

    private EntityManager entityManager;

    public ModifyProjects(EntityManager entityManager) {
        this.entityManager = entityManager;
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

}
