package test.intdmp.core.model.projects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import test.intdmp.core.model.person.messages.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String firstName;
    public String lastName;
    public String role;
    public String username;

    @OneToMany(mappedBy = "person")
    private Set<test.intdmp.core.model.projects.PersonsProjects> PersonsProjects = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<CategoriesMessages> categories = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<DataMessages> dataMessages = new HashSet<>();
    @JsonManagedReference(value="replyPerson")
    @OneToMany(mappedBy = "person")
    private Set<DataReplyMessages> dataReplyMessages = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<ReceivedMessages> receivedMessages = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<InformationOnlyMessages> informationOnlyMessages = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName;}

    public void setLastName(String firstName) { this.lastName = lastName; }

    public String getRole() { return role;}

    public void setRole(String role) { this.role = role; }

    public String getUserName() { return username;}

    public void setUserName(String username) { this.username = username; }

    @JsonIgnoreProperties("person")
    public Set<PersonsProjects> getProjects() {
        return PersonsProjects;
    }

    public Set<CategoriesMessages> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoriesMessages> categories) {
        this.categories = categories;
    }

    @JsonIgnore
    public Set<DataMessages> getDataMessages() {
        return dataMessages;
    }

    @JsonIgnore
    public void setDataMessages(Set<DataMessages> dataMessage) {
        this.dataMessages = dataMessage;
    }

    @JsonIgnore
    public Set<ReceivedMessages> getReceivedMessages() {
        return receivedMessages;
    }

    @JsonIgnore
    public Set<InformationOnlyMessages> getInformationOnlyMessages() {
        return informationOnlyMessages;
    }



}