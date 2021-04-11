package test.intdmp.core.service.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.service.repository.ProjectRepository;

import javax.persistence.EntityManager;

@Component
public class ModifyProjects {

    @Autowired
    private ProjectRepository projectRepository;

    public void updateProject(Integer projectId, Project project) {

        Project toUpdate = projectRepository.getOneById(projectId);
        toUpdate.setName(project.getName());
        toUpdate.setComplete(project.isComplete());
        toUpdate.setNumber(project.getNumber());
        projectRepository.save(toUpdate);


    }

    public void removeProject(Integer projectId) {

        projectRepository.deleteById(projectId);

    }

}
