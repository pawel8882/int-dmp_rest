package test.intdmp.core.service.fileManager;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.files.FileIndex;
import test.intdmp.core.model.files._enum.ElementStatus;
import test.intdmp.core.model.files._enum.FileType;
import test.intdmp.core.model.files._enum.UploadStatus;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.projects.SectionDepartments;
import test.intdmp.core.service.fileManager._class.FileFormatException;
import test.intdmp.core.service.fileManager._class.FileNameErrorException;

import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

@Component
public class FileIndexGenerator {

    public FileIndex generateFileIndex(MultipartFile file, Person person, Project project, String annotations) throws FileNameErrorException {
        FileIndex fileIndex = new FileIndex(person);
        String[] fileParts = file.getOriginalFilename().split("[.]");
        String fileName = fileParts[0];
        String fileFormat = fileParts[1].toUpperCase(Locale.ROOT);
        String[] fileNameParts = fileName.split("_");
        if (fileNameParts.length == 6) {
            fileIndex.setProjectName(addProjectNumberToFileIndex(project.getNumber(), fileNameParts[0]));
            fileIndex.setSectionDepartmentName(addSectionDepartmentNameToFileIndex(project.getSectionsDepartments(), fileNameParts[1]));
            fileIndex.setDepartmentNameAndDepartment(addDepartmentNameToFileIndex(project.getDepartments(), fileNameParts[2]));
            fileIndex.setElementType(addDrawingTypeName("", fileNameParts[3]));
            fileIndex.setDrawingNumber(addDrawingNumber(fileNameParts[4]));
            fileIndex.setRevision(addRevisionMark(fileNameParts[5]));
            fileIndex.setStatus(ElementStatus.IN_PROGRESS);
            fileIndex.setAnnotations(annotations);
            try {
                fileIndex.setFileFormat(FileType.valueOf(fileFormat));
            } catch (IllegalArgumentException e) {
                throw new FileFormatException(UploadStatus.FILE_FORMAT_ERROR);
            }
            return fileIndex;
        }

        else {
            throw new FileNameErrorException(UploadStatus.DRAWINGNAME_ERROR);
        }

    }

    private Character addRevisionMark(String revString) throws FileNameErrorException {
        if (revString.length() == 1)
            return revString.charAt(0);
        else
            throw new FileNameErrorException(UploadStatus.REVISION_ERROR);
    }

    private String addDrawingNumber(String fileNumber) throws FileNameErrorException {
        if (fileNumber.length() == 5)
            return fileNumber;
        else
            throw new FileNameErrorException(UploadStatus.DRAWINGNUMBER_ERROR);
    }

    private String addDrawingTypeName(String type, String fileName) throws FileNameErrorException {
        StringBuilder sb = new StringBuilder(fileName);
        if (sb.length() == 2)
            return fileName;
        else
            throw new FileNameErrorException(UploadStatus.DRAWINGTYPE_ERROR);
    }

    private Set<Department> addDepartmentNameToFileIndex(Set<Department> departments, String fileName) throws FileNameErrorException {
        Set<Department> departmentsFiltered = new HashSet<>();
        if (departments.stream().filter(e -> e.getCode().equals(fileName)).map(u -> departmentsFiltered.add(u)).findFirst().isPresent())
            if (departmentsFiltered.size() == 1)
                return departmentsFiltered;
            else
                throw  new FileNameErrorException(UploadStatus.DEPARTMENT_ERROR_TOOMANY);
        else
            throw new FileNameErrorException(UploadStatus.DEPARTMENT_ERROR);
    }

    private String addSectionDepartmentNameToFileIndex(Set<SectionDepartments> sectionDepartments, String fileName) throws FileNameErrorException {
        Set<String> sectionDepartmentsCode = new HashSet<>();
        sectionDepartments.forEach(e -> sectionDepartmentsCode.add(e.getCode()));
        if (sectionDepartmentsCode.stream().anyMatch(e -> fileName.equals(e)))
            return fileName;
        else
            throw new FileNameErrorException(UploadStatus.SECTIONDEPARTMENT_ERROR);
    }

    private String addProjectNumberToFileIndex(String projectNumber, String fileName) throws FileNameErrorException {
        if (fileName.equals(projectNumber))
            return projectNumber;
        else
            throw new FileNameErrorException(UploadStatus.PROJECT_ERROR);
    }

}
