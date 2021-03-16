package test.intdmp.core.model.person.messages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import test.intdmp.core.service.messages._class.SuggestPerson;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.messages.*;

import javax.persistence.*;

@Entity
public class DataReplyMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @OneToOne
    @JoinColumn(name = "reply_message_id")
    private ReplyMessage replyMessage;

    @ManyToOne
    @JoinColumn(name = "data_messages_id")
    private DataMessages dataMessages;

    public ReplyMessage getReplyMessage() { return replyMessage; }
    public void setReplyMessage(ReplyMessage replyMessage) { this.replyMessage = replyMessage; }

    @JsonIgnore
    public Person getPerson() { return person; }
    public void setPerson(Person person) { this.person = person; }

    public DataMessages getDataMessages() { return dataMessages; }
    public void setDataMessages(DataMessages dataMessages) { this.dataMessages = dataMessages; }

    public SuggestPerson getSuggestPerson() { return new SuggestPerson(person.getId(), person.getFirstName() + " " + person.getLastName()); }
}