package test.intdmp.core.model.person.messages;
import com.fasterxml.jackson.annotation.JsonIgnore;
import test.intdmp.core.helpClass.SuggestPerson;
import test.intdmp.core.model.projects.Person;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
public class InfoAboutMessages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne(mappedBy = "info")
    private SentMessages sentMessage;

    @OneToOne(mappedBy = "info")
    private ReceivedMessages receivedMessage;

    @OneToOne(mappedBy = "info")
    private InformationOnlyMessages informationOnlyMessage;

    private Boolean pinned;

    public Integer getId() {
        return id;
    }

    public Boolean getPinned() { return pinned; }
    public void setPinned(Boolean pinned) {  this.pinned = pinned; }




}