package test.intdmp.core.model.person.messages;

import javax.persistence.*;

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

    public SentMessages getSentMessages() { return sentMessage; }
    public ReceivedMessages getReceivedMessages() { return receivedMessage; }
    public InformationOnlyMessages getInformationOnlyMessage() { return informationOnlyMessage; }




}