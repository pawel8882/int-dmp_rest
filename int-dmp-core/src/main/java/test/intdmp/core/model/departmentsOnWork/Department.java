package test.intdmp.core.model.departmentsOnWork;

import com.fasterxml.jackson.annotation.*;
import test.intdmp.core.model.files.FileIndex;
import test.intdmp.core.model.projects.SectionDepartments;
import test.intdmp.core.service.fileManager._class.TreeNode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
public class Department implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String code;
    private String expandedIcon;
    private String collapsedIcon;

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
    public String getCode() {
        return code;
    }
    @JsonIgnore
    public Set<FileIndex> getFiles() {
        return files;
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

    @Transient
    public ArrayList<TreeNode> getListOfFilesLikeTreeNode() {
        ArrayList<FileIndex> files = new ArrayList(getFiles());
        files.sort(Comparator.comparing(FileIndex::getTitle));

        ArrayList<TreeNode> children = new ArrayList<>();

        for (FileIndex file : files)
            children.add(new TreeNode(file.getTitle() + "." + file.getFileFormat().toString(),
                    file.getFileFormat().getIcon()));

        return children;
    }

}