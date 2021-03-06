package test.intdmp.core.model.projects;

import com.fasterxml.jackson.annotation.*;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.person.messages.DataMessages;

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
    private Set<test.intdmp.core.model.projects.PersonsProjects> PersonsProjects = new HashSet<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SectionDepartments> sectionsDepartments = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<DataMessages> dataMessages = new HashSet<>();


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

    @JsonIgnore
    public Set<DataMessages> getDataMessages() {
        return dataMessages;
    }

    public void setDataMessages(Set<DataMessages> dataMessage) {
        this.dataMessages = dataMessage;
    }

    @JsonIgnoreProperties("project")
    public Set<PersonsProjects> getPersons() {
        return PersonsProjects;
    }


}
