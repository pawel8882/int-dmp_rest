package test.intdmp.core.model.person.messages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import test.intdmp.core.helpClass.SuggestPerson;
import test.intdmp.core.model.projects.Person;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ReceivedMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "data_messages_id")
    private DataMessages dataMessages;

    @ManyToOne
    @JoinColumn(name = "categories_messages_id")
    private CategoriesMessages category;

    /* A = sentMessage, B = receivedMessage, C = InformationOnly */
    private Character type;

    private Boolean wasOpened;

    public Timestamp timestamp;

    public Integer getId() {
        return id;
    }

    public DataMessages getDataMessages() { return dataMessages; }
    public void setDataMessages(DataMessages dataMessages) { this.dataMessages = dataMessages; }

    @JsonIgnore
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public Character getType() { return type; }

    public Boolean getOpened() { return wasOpened; }
    public void setOpened(Boolean opened) { this.wasOpened = opened;  }

    public CategoriesMessages getCategory() { return category; }
    public void SetCategoriesMessages(CategoriesMessages category) {this.category = category;}

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }




}