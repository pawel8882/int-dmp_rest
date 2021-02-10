package test.intdmp.core.model.projects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

@Entity
public class PersonsProjects {

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