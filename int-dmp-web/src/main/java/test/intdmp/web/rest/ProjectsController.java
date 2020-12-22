package test.intdmp.web.rest;

import org.springframework.web.bind.annotation.*;
import test.intdmp.core.model.Project;
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
    public List<Project> getProjectList() {
        return projectService.getProjectList();
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET)
    public Project getProject(@PathVariable("projectId") Integer projectId) {
        return projectService.getProject(projectId);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Integer createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.PUT)
    public void updateProject(@RequestParam("projectId") Integer projectId, @RequestBody Project project) {
        projectService.updateProject(projectId, project);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.DELETE)
    public void removeProject(@PathVariable("projectId") Integer projectId) {
        projectService.removeProject(projectId);
    }
}
