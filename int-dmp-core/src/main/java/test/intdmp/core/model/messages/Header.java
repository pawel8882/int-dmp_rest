package test.intdmp.core.model.messages;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import test.intdmp.core.model.person.messages.DataMessages;

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
    public Timestamp timestamp;
    public String date;
    public String title;
    public String concerns;

    @JsonManagedReference
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "message_id")
    private Message message;

    @OneToMany(mappedBy = "header")
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
        Date date = new Date(timestamp.getTime());
        SimpleDateFormat simple = new SimpleDateFormat("dd.MM.yyy HH:mm:ss");
        this.date = simple.format(date);
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

    public String getDate() {
        return date;
    }


}