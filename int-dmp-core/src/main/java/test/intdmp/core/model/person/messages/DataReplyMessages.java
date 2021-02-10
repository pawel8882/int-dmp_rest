package test.intdmp.core.model.person.messages;
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

    @ManyToOne
    @JoinColumn(name = "reply_message_id")
    private ReplyMessage replyMessage;

    private Boolean update;

    public ReplyMessage getReplyMessage() { return replyMessage; }

    public Boolean getUpdate() { return update; }



}