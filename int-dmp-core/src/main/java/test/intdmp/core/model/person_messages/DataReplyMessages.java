package test.intdmp.core.model.person_messages;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import test.intdmp.core.model.Person;
import test.intdmp.core.model.messages.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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