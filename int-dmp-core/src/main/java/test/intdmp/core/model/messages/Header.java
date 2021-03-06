package test.intdmp.core.model.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import test.intdmp.core.model.person.messages.DataMessages;
import test.intdmp.core.model.projects.Project;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Header implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String title;
    public String concerns;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToOne(mappedBy = "header")
    private DataMessages dataMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title; }

    public String getConcerns() { return concerns;}

    public void setConcerns(String concerns) { this.concerns = concerns; }

    @JsonIgnoreProperties("content")
    public Message getMessage() {
        return message;
    }
    public void setMessage(Message message) { this.message = message; }

    @JsonIgnore
    public DataMessages getDataMessages() { return dataMessage; }


}