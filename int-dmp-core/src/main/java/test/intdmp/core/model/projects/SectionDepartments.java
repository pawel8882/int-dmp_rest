package test.intdmp.core.model.projects;

import com.fasterxml.jackson.annotation.*;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.files.FileIndex;
import test.intdmp.core.service.fileManager._class.TreeNode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class SectionDepartments implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String section;
    private String description;
    private String code;
    private String expandedIcon;
    private String collapsedIcon;

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
    public String getCode() {
        return code;
    }

    @JsonIgnoreProperties("sectionDepartment")
    public List<Department> getDepartments() {
        List <Department> departmentsSorted =new ArrayList<>(departments);
        departmentsSorted.sort(Comparator.comparing(Department::getName));
        return departmentsSorted;
    }
    public void setDepartments(Set<Department> departments) { this.departments = departments; }

    @Transient
    public ArrayList<TreeNode> getDepartmentsLikeTreeNode() {
        ArrayList<Department> departments = new ArrayList(getDepartments());
        departments.sort(Comparator.comparing(Department::getCode));
        ArrayList<TreeNode> children = new ArrayList<>();
        for (Department d : departments)
            children.add(new TreeNode(d.getCode(), d.getExpandedIcon(), d.getCollapsedIcon(), d.getListOfFilesLikeTreeNode()));
        return children;
    }

    @JsonIgnore
    public Project getProject() {
        return project;
    }
    public void setProject(Project project) {
        this.project = project;
    }

    public String getExpandedIcon() {
        return this.expandedIcon;
    }
    public void setExpandedIcon(String s) {
        this.expandedIcon = s;
    }
    public String getCollapsedIcon() {
        return this.collapsedIcon;
    }
    public void setCollapsedIcon(String s) {
        this.collapsedIcon = s;
    }

    public SectionDepartments(){
        this.collapsedIcon = "pi pi-folder";
        this.expandedIcon = "pi pi-folder-open";
    }


}