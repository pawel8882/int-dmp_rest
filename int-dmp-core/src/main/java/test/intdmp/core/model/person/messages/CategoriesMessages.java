package test.intdmp.core.model.person.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import test.intdmp.core.model.projects.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
public class CategoriesMessages implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String category;
    public String color;

    @JsonBackReference
    @ManyToOne
    public Person person;

    @OneToMany(mappedBy = "category")
    private Set<InformationOnlyMessages> informationOnlyMessages = new HashSet<>();

    @OneToMany(mappedBy = "category")
    private Set<SentMessages> sentMessages = new HashSet<>();

    @OneToMany(mappedBy = "category")
    private Set<ReceivedMessages> receivedMessages = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category; }

    public String getColor() { return color;}

    public void setColor(String color) { this.color = color; }

    @JsonIgnore
    public Set<SentMessages> getSentMessages() { return sentMessages; }
    @JsonIgnore
    public Set<SentMessages> getPinnedSentMessages() { return sentMessages.stream().filter(e -> e.getInfo()
            .getPinned()
            .equals(true))
            .collect(Collectors.toSet()); }
    public void setSentMessages(Set<SentMessages> sentMessages) { this.sentMessages = sentMessages; }

    @JsonIgnore
    public Set<InformationOnlyMessages> getInformationOnlyMessages() { return informationOnlyMessages; }
    @JsonIgnore
    public Set<InformationOnlyMessages> getPinnedInformationOnlyMessages() { return informationOnlyMessages.stream().filter(e -> e.getInfo()
            .getPinned().equals(true))
            .collect(Collectors.toSet()); }
    public void setInformationOnlyMessages(Set<InformationOnlyMessages> informationOnlyMessages) { this.informationOnlyMessages = informationOnlyMessages; }

    @JsonIgnore
    public Set<ReceivedMessages> getReceivedMessages() { return receivedMessages; }
    @JsonIgnore
    public Set<ReceivedMessages> getPinnedReceivedMessages() { return receivedMessages.stream().filter(e -> e.getInfo()
            .getPinned().equals(true))
            .collect(Collectors.toSet()); }
    public void setReceivedMessages(Set<ReceivedMessages> receivedMessages) { this.receivedMessages = receivedMessages; }


}
