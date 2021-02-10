package test.intdmp.core.model.department.objects;

import com.fasterxml.jackson.annotation.*;
import test.intdmp.core.model.projects.Department;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class menu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer placement;
    public String label;
    public String icon;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<menuLevel1> items = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    private Department department;

    private Integer getId() {
        return id;
    }

    private void setId(Integer id) {
        this.id = id;
    }

    public Integer getPlacement() {
        return placement;
    }

    public void setPlacement(Integer id) {
        this.placement = placement;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public TreeSet<menuLevel1> getItems() {

        Comparator<menuLevel1> comparator = Comparator.comparing(menuLevel1::getPlacement);
        TreeSet<menuLevel1> menu_sorted = new TreeSet<>(comparator);
        for (menuLevel1 menu: items) menu_sorted.add(menu);
        return menu_sorted;

    }

    public void setItems(Set<menuLevel1> item) {
        this.items = item;
    }

}