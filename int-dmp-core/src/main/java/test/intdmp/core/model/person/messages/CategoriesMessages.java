package test.intdmp.core.model.person.messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import test.intdmp.core.model.projects.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

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
    private Set<DataMessages> dataMessages = new HashSet<>();

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
    public Set<DataMessages> getDataMessages() { return dataMessages; }
    public void setDataMessages(Set<DataMessages> dataMessages) { this.dataMessages = dataMessages; }

    @JsonIgnore
    public Set<InformationOnlyMessages> getInformationOnlyMessages() { return informationOnlyMessages; }
    public void setInformationOnlyMessages(Set<InformationOnlyMessages> informationOnlyMessages) { this.informationOnlyMessages = informationOnlyMessages; }

    @JsonIgnore
    public Set<ReceivedMessages> getReceivedMessages() { return receivedMessages; }
    public void setReceivedMessages(Set<ReceivedMessages> receivedMessages) { this.receivedMessages = receivedMessages; }


}
