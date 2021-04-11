package test.intdmp.core.model.projects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
public class PersonsProjects {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    private Timestamp addingDate;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @JsonIgnoreProperties("persons")
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @JsonIgnoreProperties("projects")
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }


    public Timestamp getAddingDate() {
        return addingDate;
    }
    public void setAddingDate(Timestamp timestamp) {
        this.addingDate = timestamp;
    }

}