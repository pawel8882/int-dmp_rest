package test.intdmp.core.model.projects;

import com.fasterxml.jackson.annotation.*;
import test.intdmp.core.model.departmentsOnWork.Department;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class SectionDepartments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String section;
    public String description;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name ="project_id")
    public Project project;

    @ManyToMany(mappedBy = "sectionDepartments")
    public Set<Person> persons;

    @OneToMany(mappedBy = "sectionDepartment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Department> departments;

    public Integer getId() {
        return id;
    }
    public String getSection() {
        return section;
    }
    public String getDescription() {
        return description;
    }

    @JsonIgnoreProperties("sectionDepartment")
    public List<Department> getDepartments() {
        List <Department> departmentsSorted =new ArrayList<>(departments);
        departmentsSorted.sort(Comparator.comparing(Department::getName));
        return departmentsSorted;
    }
    public void setDepartments(Set<Department> departments) { this.departments = departments; }

    @JsonIgnore
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }


}