package test.intdmp.core.model.person_messages;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import test.intdmp.core.model.Person;
import test.intdmp.core.model.messages.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class DataMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @ManyToOne
    @JoinColumn(name = "header_id")
    private Header header;

    /* A = sentMessage, B = receivedMessage, C = InformationOnly */
    private Character type;

    private Boolean wasOpened;

    private String category;

    public Header getHeader() { return header; }

    public Character getType() { return type; }

    public Boolean getOpened() { return wasOpened; }

    public String getCategory() { return category; }


}