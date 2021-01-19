package test.intdmp.core.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import test.intdmp.core.model.person_messages.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Person implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String firstName;
    public String lastName;
    public String role;

    @OneToMany(mappedBy = "person")
    private Set<persons_projects> persons_projects = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<CategoriesMessages> categories = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<DataMessages> dataMessages = new HashSet<>();
    @OneToMany(mappedBy = "person")
    private Set<DataReplyMessages> dataReplyMessages = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() { return lastName;}

    public void setLastName(String firstName) { this.lastName = lastName; }

    public String getRole() { return role;}

    public void setRole(String role) { this.role = role; }

    @JsonIgnoreProperties("person")
    public Set<persons_projects> getProjects() {
        return persons_projects;
    }

    public Set<CategoriesMessages> getCategories() {
        return categories;
    }

    public void setCategories(Set<CategoriesMessages> categories) {
        this.categories = categories;
    }


}