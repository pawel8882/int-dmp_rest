package test.intdmp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.projects.SectionDepartments;
import test.intdmp.core.service.project.CollectProjects;
import test.intdmp.core.service.project.ModifyProjects;
import test.intdmp.core.service.project.AddProjects;

import java.util.*;

@Service
@Transactional
public class ProjectService {

    @Autowired
    private CollectProjects collectProjects;
    @Autowired
    private AddProjects addProjects;
    @Autowired
    private ModifyProjects modifyProjects;



    public List<Project> getProjectList(String user) {
        List<Project> projectList = collectProjects.getUserProjects(user);
        return projectList;
    }

    public Project getProject(Integer projectId) {
        return collectProjects.getOneProject(projectId);
    }

    public List<SectionDepartments> getDepartmentsForProject(Integer projectId, String user) {
        return collectProjects.userDepartments(projectId, user);
    }

    public Integer createProject(Project project, String user) {
        return addProjects.createProject(project, user);
    }

    public void updateProject(Integer projectId, Project project) {
        modifyProjects.updateProject(projectId, project);
    }

    public void removeProject(Integer projectId) {
        modifyProjects.removeProject(projectId);
    }

}
