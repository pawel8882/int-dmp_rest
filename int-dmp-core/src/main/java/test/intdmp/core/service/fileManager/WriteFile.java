package test.intdmp.core.service.fileManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.files.*;
import test.intdmp.core.model.files._enum.ElementStatus;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.service.fileManager._class.*;
import test.intdmp.core.service.repository.FileSystemRepository;
import test.intdmp.core.service.repository.FileIndexRepository;
import test.intdmp.core.service.repository.PersonRepository;
import test.intdmp.core.service.repository.DepartmentRepository;

import java.io.IOException;

@Service
public class WriteFile {

    @Autowired
    private FileSystemRepository fileSystemRepository;
    @Autowired
    private FileIndexRepository fileIndexRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private DepartmentRepository departmentRepository;

    public void writeFileAndSetFileIndex(byte[] content, FileIndex fileIndex) throws IOException {

        String subFolder = fileIndex.getProjectName() + "\\" + fileIndex.getSectionDepartmentName() + "\\" + fileIndex.getDepartmentName() +"\\";
        String location = fileSystemRepository.save(content, subFolder + fileIndex.getTitle());
        fileIndex.setLocation(location);

        fileIndexRepository.save(fileIndex);

    }

}
