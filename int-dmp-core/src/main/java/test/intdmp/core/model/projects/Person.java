package test.intdmp.core.model.projects;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import test.intdmp.core.model.person.messages.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

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
    @OneToMany(mappedBy = "person")
    private Set<DataReplyMessages> dataReplyMessages = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<ReceivedMessages> receivedMessages = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<InformationOnlyMessages> informationOnlyMessages = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<SentMessages> sentMessages= new HashSet<>();
    @ManyToMany
    @JoinTable(
            name = "section_departments_persons",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "section_department_id"))
    private Set<SectionDepartments> sectionDepartments;

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

    @JsonIgnore
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
    public Set<ReceivedMessages> getPinnedReceivedMessages() { return receivedMessages.stream()
            .filter(e -> e.getInfo()
                    .getPinned()
                    .equals(true))
            .collect(Collectors.toSet()); }


    @JsonIgnore
    public Set<InformationOnlyMessages> getInformationOnlyMessages() {
        return informationOnlyMessages;
    }
    @JsonIgnore
    public Set<InformationOnlyMessages> getPinnedInformationOnlyMessages() { return informationOnlyMessages.stream()
            .filter(e -> e.getInfo()
                    .getPinned()
                    .equals(true) && e.getInfo().getReceivedMessages() == null)
            .collect(Collectors.toSet());
    }

    @JsonIgnore
    public Set<DataReplyMessages> getDataReplyMessages() {
        return dataReplyMessages;
    }

    @JsonIgnore
    public Set<SentMessages> getSentMessages() { return sentMessages; }
    @JsonIgnore
    public Set<SentMessages> getPinnedSentMessages() { return sentMessages.stream()
            .filter(e -> e.getInfo()
                    .getPinned()
                    .equals(true) && e.getInfo().getReceivedMessages() == null && e.getInfo().getInformationOnlyMessage() == null)
            .collect(Collectors.toSet()); }
    public void setSentMessages(Set<SentMessages> sentMessages) { this.sentMessages = sentMessages; }

    @JsonIgnore
    public Set<SectionDepartments> getSectionDepartments() {
        return sectionDepartments;
    }
    public void setSectionDepartments(Set<SectionDepartments> sectionDepartments) { this.sectionDepartments = sectionDepartments; }



}