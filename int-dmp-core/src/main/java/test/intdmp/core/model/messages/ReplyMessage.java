package test.intdmp.core.model.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import test.intdmp.core.service.messages._class.SuggestPerson;
import test.intdmp.core.model.person.messages.DataReplyMessages;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ReplyMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Timestamp timestamp;
    @Column(length = 5000000)
    public String content;

    @JsonBackReference(value="reply")
    @ManyToOne
    public Message message;

    @OneToOne(mappedBy = "replyMessage", cascade = CascadeType.ALL)
    private DataReplyMessages dataReplyMessages;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content; }

    @JsonIgnore
    public Message getMessage() {
        return message;
    }
    public void setMessage(Message message) { this.message = message; }

    @JsonIgnoreProperties({"replyMessage", "dataMessages"})
    public DataReplyMessages getDataReplyMessage() {
        return dataReplyMessages;
    }

    public SuggestPerson getOwner() { return dataReplyMessages.getSuggestPerson(); }

    public Set<SuggestPerson> getOwnerLikeSet() {
        Set<SuggestPerson> persons = new HashSet<>();
        persons.add(dataReplyMessages.getSuggestPerson());
        return persons;
    }


}