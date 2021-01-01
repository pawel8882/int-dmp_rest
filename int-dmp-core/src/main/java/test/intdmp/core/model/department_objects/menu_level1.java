package test.intdmp.core.model.department_objects;

import com.fasterxml.jackson.annotation.*;
import test.intdmp.core.model.Project;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

@Entity
public class menu_level1 implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer placement;
    public String label;
    public String icon;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @OneToMany(mappedBy = "menu_level1", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<menu_level2> items = new HashSet<>();

    @JsonBackReference
    @ManyToOne
    public menu menu;

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

    public TreeSet<menu_level2> getItems() {

        Comparator<menu_level2> comparator = Comparator.comparing(menu_level2::getPlacement);
        TreeSet<menu_level2> menu_sorted = new TreeSet<>(comparator);
        for (menu_level2 menu: items) menu_sorted.add(menu);
        return menu_sorted;

    }

    public void setItems(Set<menu_level2> item) {
        this.items = item;
    }

}