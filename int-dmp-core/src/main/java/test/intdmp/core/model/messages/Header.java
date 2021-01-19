package test.intdmp.core.model.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import test.intdmp.core.model.person_messages.DataMessages;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Header implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public Timestamp timestamp;
    public String title;
    public String concerns;

    @OneToOne
    private Message message;

    @JsonBackReference
    @OneToMany(mappedBy = "person")
    private Set<DataMessages> dataMessages = new HashSet<>();

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

    public String getTitle() { return title;}

    public void setTitle(String title) { this.title = title; }

    public String getConcerns() { return concerns;}

    public void setConcerns(String concerns) { this.concerns = concerns; }

    public Message getMessage() {
        return message;
    }


}