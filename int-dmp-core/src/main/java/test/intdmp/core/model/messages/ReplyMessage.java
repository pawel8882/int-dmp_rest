package test.intdmp.core.model.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    public String content;

    @JsonBackReference(value="reply")
    @ManyToOne
    public Message message;

    @JsonBackReference(value="personReply")
    @OneToMany(mappedBy = "person")
    private Set<DataReplyMessages> dataReplyMessages = new HashSet<>();

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

    public Message getMessage() {
        return message;
    }


}