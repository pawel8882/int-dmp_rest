package test.intdmp.core.model.files;

import com.fasterxml.jackson.annotation.JsonBackReference;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.files._enum.ElementStatus;
import test.intdmp.core.model.files._enum.FileType;
import test.intdmp.core.model.projects.Person;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class FileIndex implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String location;

    private String title;
    private FileType fileFormat;
    private String annotations;
    private ElementStatus status;

    public FileIndex(String location, Person person, Department department) {
        this.location = location;
        this.person = person;
        this.department = department;
    }

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

    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}

    public String getAnnotations() {return annotations;}
    public void setAnnotations(String annotations) {this.annotations = annotations;}

    public ElementStatus getStatus() {return status;}
    public void setStatus(ElementStatus status) {this.status = status;}

    public FileType getFileFormat() {return fileFormat;}
    public void setFileFormat(FileType fileType) {this.fileFormat = fileType;}

}
