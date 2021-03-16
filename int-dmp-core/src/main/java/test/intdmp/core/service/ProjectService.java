package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.model.projects.SectionDepartments;
import test.intdmp.core.service.project.GetProjects;
import test.intdmp.core.service.project.ModifyProjects;
import test.intdmp.core.service.project.SetProjects;

import javax.persistence.EntityManager;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class ProjectService {

    private GetProjects getProjects;
    private SetProjects setProjects;
    private ModifyProjects modifyProjects;

    public ProjectService(GetProjects getProjects, SetProjects setProjects, ModifyProjects modifyProjects) {
        this.getProjects = getProjects;
        this.setProjects = setProjects;
        this.modifyProjects = modifyProjects;
    }

    public List<Project> getProjectList(String user) {
        List<Project> projectList = getProjects.getUserProjects(user);
        return projectList;
    }

    public Project getProject(Integer projectId) {
        return getProjects.getOneProject(projectId);
    }

    public List<SectionDepartments> getDepartmentsForProject(Integer projectId, String user) {
        return getProjects.userDepartments(projectId, user);
    }

    public Integer createProject(Project project) {
        return setProjects.createProject(project);
    }

    public void updateProject(Integer projectId, Project project) {
        modifyProjects.updateProject(projectId, project);
    }

    public void removeProject(Integer projectId) {
        modifyProjects.removeProject(projectId);
    }

}
