package test.intdmp.core.model.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import test.intdmp.core.model.person.messages.DataMessages;
import test.intdmp.core.model.projects.Project;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Timestamp timestamp;
    @Column(length = 5000000)
    private String content;

    @JsonManagedReference(value="reply")
    @JsonIgnore
    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL)
    @OrderBy("timestamp DESC")
    private List<ReplyMessage> ReplyMessages = new ArrayList<>();

    @JsonBackReference
    @OneToOne(mappedBy = "message")
    private Header header;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) { this.timestamp = timestamp; }

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content; }

    public List<ReplyMessage> getReplyMessages() { return ReplyMessages; }
    public void addReplyMessage(ReplyMessage replyMessage) { ReplyMessages.add(replyMessage); }

    public Header getHeader() {
        return header;
    }




}