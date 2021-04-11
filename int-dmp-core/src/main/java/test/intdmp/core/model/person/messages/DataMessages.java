package test.intdmp.core.model.person.messages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.service.messages._class.SuggestPerson;
import test.intdmp.core.model.messages.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.*;

@Entity
public class DataMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "header_id")
    private Header header;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "dataMessages")
    private Set<ReceivedMessages> receivedMessages = new HashSet<>();

    @OneToMany(mappedBy = "dataMessages")
    private Set<InformationOnlyMessages> informationOnlyMessages = new HashSet<>();

    @OneToMany(mappedBy = "dataMessages")
    private Set<DataReplyMessages> dataReplyMessages = new HashSet<>();

    @OneToMany(mappedBy = "dataMessages")
    private Set<SentMessages> sentMessages = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public Header getHeader() { return header; }
    public void setHeader(Header header) { this.header = header; }

    public String getPersonName() { return person.getFirstName(); }
    public String getPersonLastName() { return person.getLastName(); }

    @JsonIgnore
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    @JsonIgnore
    public Project getProject() { return project; }
    public void setProject(Project project) { this.project = project; }

    @JsonIgnore
    public Set<DataReplyMessages> getDataReplyMessages() { return dataReplyMessages; }
    public void setDataReplyMessage(Set<DataReplyMessages> replyMessages) { this.dataReplyMessages = replyMessages; }

    @JsonIgnore
    public Set<ReceivedMessages> getReceivedMessages() { return receivedMessages; }
    public void setReceivedMessages(Set<ReceivedMessages> receivedMessages) { this.receivedMessages = receivedMessages; }

    @JsonIgnore
    public Set<InformationOnlyMessages> getInformationOnlyMessages() { return informationOnlyMessages; }
    public void setInformationOnlyMessages(Set<InformationOnlyMessages> informationOnlyMessages) { this.informationOnlyMessages = informationOnlyMessages; }

    @JsonIgnore
    public Set<SentMessages> getSentMessages() { return sentMessages; }
    public void setSentMessages(Set<SentMessages> sentMessages) { this.sentMessages = sentMessages; }



    public Set<SuggestPerson> getPersonsDO() {
        Set<SuggestPerson> persons = new HashSet<>();
        this.receivedMessages.forEach(e -> {
            SuggestPerson person = new SuggestPerson(e.getPerson().getId(), e.getPerson().getFirstName() + " " + e.getPerson().getLastName());
            persons.add(person);
        });
        return persons;

    }

    public Set<SuggestPerson> getPersonsDW() {
        Set<SuggestPerson> persons = new HashSet<>();
        this.informationOnlyMessages.forEach(e -> {
            SuggestPerson person = new SuggestPerson(e.getPerson().getId(), e.getPerson().getFirstName() + " " + e.getPerson().getLastName());
            persons.add(person);
        });
        return persons;

    }

    @Transient
    public Set<SuggestPerson> getPersonsWithoutCurrentUser(Integer personId) {
        Set<SuggestPerson> persons = new HashSet<>();
        this.receivedMessages.forEach(e -> {
            if(e.getPerson().getId() != personId) {
                SuggestPerson person = new SuggestPerson(e.getPerson().getId(), e.getPerson().getFirstName() + " " + e.getPerson().getLastName());
                persons.add(person);
            }
        });
        return persons;

    }

    @Transient
    private SuggestPerson getOwner() {
        SuggestPerson person = new SuggestPerson(getPerson().getId(), getPerson().getFirstName() + " " + getPerson().getLastName());
        return person;
    }

    @Transient
    public Set<SuggestPerson> getPersonLikeSuggestPersonTheNewest(Integer personId) {
        Set<SuggestPerson> persons = new HashSet<>();
            persons.add(getOwner());
        return persons;

    }

    @Transient
    public ReplyMessage getPersonReplyMessageTheNewest(Integer personId) {
        ReplyMessage replyMessage =
            getHeader().getMessage().getReplyMessages().stream().filter(e -> e.getOwner().id != personId).max(Comparator.comparing(ReplyMessage::getTimestamp))
                    .get();

        return replyMessage;

    }

    }
