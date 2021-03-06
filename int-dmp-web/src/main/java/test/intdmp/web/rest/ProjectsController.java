package test.intdmp.web.rest;

import org.springframework.web.bind.annotation.*;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.projects.SectionDepartments;
import test.intdmp.core.service.ProjectService;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectsController {

    private ProjectService projectService;

    ProjectsController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Project> getProjectList(@RequestParam("user") String user) {
        return projectService.getProjectList(user);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public Project getProject(@PathVariable("projectId") Integer projectId) {
        return projectService.getProject(projectId);
    }

    @RequestMapping(value = "/{projectId}/departments", method = RequestMethod.GET)
    public List<SectionDepartments> getDepartmentsForProject(@PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return projectService.getDepartmentsForProject(projectId, user);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Integer createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
    public void updateProject(@PathVariable("projectId") Integer projectId, @RequestBody Project project) {
        projectService.updateProject(projectId, project);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public void removeProject(@PathVariable("projectId") Integer projectId) {
        projectService.removeProject(projectId);
    }
}
