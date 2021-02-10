package test.intdmp.core.model.person.messages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.helpClass.SuggestPerson;
import test.intdmp.core.model.messages.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DataMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private Header header;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "dataMessages")
    private Set<ReceivedMessages> receivedMessages = new HashSet<>();

    @OneToMany(mappedBy = "dataMessages")
    private Set<InformationOnlyMessages> informationOnlyMessages = new HashSet<>();

    /* A = sentMessage, B = receivedMessage, C = InformationOnly */
    private Character type;

    private Boolean wasOpened;

    private String category;

    public Integer getId() {
        return id;
    }

    public Header getHeader() { return header; }
    public void setHeader(Header header) { this.header = header; }

    public Character getType() { return type; }

    public Boolean getOpened() { return wasOpened; }
    public void setOpened(Boolean opened) { this.wasOpened = opened;  }

    public String getCategory() { return category; }

    public String getPersonName() { return person.getFirstName(); }
    public String getPersonLastName() { return person.getLastName(); }

    @JsonIgnore
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    @JsonIgnore
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    public Set<SuggestPerson> getPersons() {
        Set<SuggestPerson> persons = new HashSet<>();
        this.receivedMessages.forEach(e -> {
            SuggestPerson person = new SuggestPerson(e.getPerson().getId(), e.getPerson().getFirstName() + " " + e.getPerson().getLastName());
            persons.add(person);
        });
        return persons;

    }





}