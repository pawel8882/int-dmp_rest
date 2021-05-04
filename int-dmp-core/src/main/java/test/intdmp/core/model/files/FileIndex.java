package test.intdmp.core.model.files;

import com.fasterxml.jackson.annotation.JsonBackReference;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.files._enum.ElementStatus;
import test.intdmp.core.model.files._enum.FileType;
import test.intdmp.core.model.projects.Person;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
public class FileIndex implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String location;

    private String projectName;
    private String sectionDepartmentName;
    private String departmentName;
    private String elementType;
    private String drawingNumber;
    private Character revision;
    private FileType fileFormat;
    private String annotations;
    private ElementStatus status;

    public FileIndex(Person person) {
        this.person = person;
    }

    public FileIndex() {}

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name ="department_id")
    public Department department;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name ="person_id")
    public Person person;

    public Integer getId() {
        return id;
    }

    public String getLocation() {return location;}
    public void setLocation(String location) {this.location = location;}

    public String getTitle() {
        StringBuilder title = new StringBuilder();
        title.append(projectName);
        title.append("_");
        title.append(sectionDepartmentName);
        title.append("_");
        title.append(departmentName);
        title.append("_");
        title.append(drawingNumber);
        title.append("_");
        title.append(revision);
        String stringTitle = title.toString();
        return stringTitle;
    }

    public String getAnnotations() {return annotations;}
    public void setAnnotations(String annotations) {this.annotations = annotations;}

    public ElementStatus getStatus() {return status;}
    public void setStatus(ElementStatus status) {this.status = status;}

    public FileType getFileFormat() {return fileFormat;}
    public void setFileFormat(FileType fileType) {this.fileFormat = fileType;}

    public String getProjectName() {return projectName;}
    public void setProjectName(String s) {this.projectName = s;}

    public String getSectionDepartmentName() {return sectionDepartmentName;}
    public void setSectionDepartmentName(String s) {this.sectionDepartmentName = s;}

    public String getDepartmentName() {return departmentName;}
    public void setDepartmentName(String s) {this.departmentName = s;}
    public void setDepartmentNameAndDepartment(Set<Department> departments) {
        Department department = departments.stream().findFirst().get();
        this.departmentName = department.getCode();
        this.department = department;
    }

    public String getElementType() {return elementType;}
    public void setElementType(String s) {this.elementType = s;}

    public String getDrawingNumber() {return drawingNumber;}
    public void setDrawingNumber(String s) {this.drawingNumber = s;}

    public Character getRevision() {return revision;}
    public void setRevision(Character s) {this.revision = s;}

}
