package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.Project;
import test.intdmp.core.model.department;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Set;

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

    public Project getProject(Integer projectId) { return entityManager.find(Project.class, projectId); }

    public List<department> getDepartmentsForProject(Integer projectId) { return departmentsForProject(projectId); }

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

    public List<department> departmentsForProject(Integer projectId) {

        return entityManager.createQuery("SELECT DISTINCT p FROM department p, IN(p.project) t WHERE t.id =: project_id order by p.name", department.class)
                .setParameter("project_id", projectId)
                .getResultList();

    }

}
