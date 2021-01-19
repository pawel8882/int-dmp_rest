package test.intdmp.core.model.person_messages;

import com.fasterxml.jackson.annotation.JsonBackReference;
import test.intdmp.core.model.Person;
import test.intdmp.core.model.messages.Message;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
public class CategoriesMessages implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String category;

    @JsonBackReference
    @ManyToOne
    public Person person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCategory() { return category;}

    public void setCategory(String category) { this.category = category; }


}
