package test.intdmp.core.model.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import test.intdmp.core.model.persons_projects;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Message implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Timestamp timestamp;
    public String content;

    @OneToMany(mappedBy = "message")
    private Set<ReplyMessage> ReplyMessages = new HashSet<>();

    @OneToOne
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

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() { return content;}

    public void setContent(String content) { this.content = content; }

    public Set<ReplyMessage> getReplyMessages() {
        return ReplyMessages;
    }

    public Header getHeader() {
        return header;
    }




}