package test.intdmp.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class ProjectDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Integer id;

    public String detailName;

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

    public String getDetailName() {
        return detailName;
    }

    public void setDetailName(String detailName) {
        this.detailName = detailName;
    }
}
