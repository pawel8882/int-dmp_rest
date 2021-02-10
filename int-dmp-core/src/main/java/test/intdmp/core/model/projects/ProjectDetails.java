package test.intdmp.core.model.projects;

import com.fasterxml.jackson.annotation.JsonBackReference;
import test.intdmp.core.model.projects.Project;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProjectDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    public String description;

    @JsonBackReference
    @ManyToOne
    public Project project;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
