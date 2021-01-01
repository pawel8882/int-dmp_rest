package test.intdmp.core.model;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private boolean complete;
    private String number;

    @JsonManagedReference
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<ProjectDetails> details = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<persons_projects> persons_projects = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<department> departments = new HashSet<>();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Set<ProjectDetails> getDetails() {
        return details;
    }

    public void setDetails(Set<ProjectDetails> details) {
        this.details = details;
    }

    @JsonIgnoreProperties("project")
    public Set<persons_projects> getPersons() {
        return persons_projects;
    }

    public Set<Integer> getDepartmentId() {

        Set<Integer> departments_id = new HashSet<>();
        for (department dp : departments) { departments_id.add(dp.getId());  }
        return departments_id;

    }

}
