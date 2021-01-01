package test.intdmp.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class persons_projects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    public String addingDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnoreProperties("persons")
    public Project getProject() { return project; }

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @JsonIgnoreProperties("projects")
    public Person getPerson() { return person; }


    public String getAddingDate() {
        return addingDate;
    }

}