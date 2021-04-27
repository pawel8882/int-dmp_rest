package test.intdmp.core.model.departmentsOnWork;

import com.fasterxml.jackson.annotation.*;
import test.intdmp.core.model.files.FileIndex;
import test.intdmp.core.model.projects.SectionDepartments;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String name;
    public String description;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name ="section_department_id")
    public SectionDepartments sectionDepartment;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<FileIndex> files;


    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public Set<FileIndex> getFiles() {
        return files;
    }

}