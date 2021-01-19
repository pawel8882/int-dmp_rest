package test.intdmp.core.model;

import com.fasterxml.jackson.annotation.*;
import test.intdmp.core.model.department;
import test.intdmp.core.model.department_objects.menu;
import test.intdmp.core.model.department_objects.menu_level1;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String name;
    public String description;

    @JsonBackReference
    @ManyToOne
    public Project project;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<menu> menu;

    public Integer getId() {
        return id;
    }

    public TreeSet<menu> getMenu() {

        Comparator<menu> comparator = Comparator.comparing(test.intdmp.core.model.department_objects.menu::getPlacement);
        TreeSet<menu> menu_sorted = new TreeSet<>(comparator);
        for (menu menu: menu) menu_sorted.add(menu);
        return menu_sorted;

    }

    public void setItems(Set<menu> menu) {
        this.menu = menu;
    }

}