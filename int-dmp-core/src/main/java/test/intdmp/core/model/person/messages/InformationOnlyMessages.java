package test.intdmp.core.model.person.messages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import test.intdmp.core.model.projects.Person;

import javax.persistence.*;

@Entity
public class InformationOnlyMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "data_messages_id")
    private DataMessages dataMessages;

    /* A = sentMessage, B = receivedMessage, C = InformationOnly */
    private Character type;

    private Boolean wasOpened;

    private String category;

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

    public String getCategory() { return category; }




}