package test.intdmp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import test.intdmp.core.model.departmentsOnWork.Department;
import test.intdmp.core.model.files.FileIndex;
import test.intdmp.core.model.files._enum.FileType;
import test.intdmp.core.model.files._enum.UploadStatus;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.projects.SectionDepartments;
import test.intdmp.core.service.fileManager.FileIndexGenerator;
import test.intdmp.core.service.fileManager.WriteFile;
import test.intdmp.core.service.fileManager._class.*;
import test.intdmp.core.service.repository.PersonRepository;
import test.intdmp.core.service.repository.ProjectRepository;

import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class FileService {

    private String error = "Error";

    /*P2021_KONS_SCIA_EL_0001_A*/

    @Autowired
    private WriteFile writeFile;
    @Autowired
    private FileIndexGenerator fileIndexGenerator;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public ArrayList<FileStatus> saveFileAndFileIndex(MultipartFile files[], FileInfo fileInfo) {
        Person person = personRepository.findByUsername(fileInfo.user);
        Project project = projectRepository.getOneById(fileInfo.projectID);

        ArrayList<FileStatus> filesStatus = new ArrayList<>();

        for (MultipartFile file : files) {
            try {
                FileIndex fileIndex = fileIndexGenerator.generateFileIndex(file, person, project, fileInfo.annotations);
                writeFile.writeFileAndSetFileIndex(file.getBytes(), fileIndex);
                FileStatus status = new FileStatus(UploadStatus.OK, file.getOriginalFilename());
                filesStatus.add(status);

            } catch (FileNameErrorException e) {
                FileStatus status = new FileStatus(e.getMessage(), file.getOriginalFilename());
                filesStatus.add(status);

            } catch (Exception e) {
                FileStatus status = new FileStatus(e.getMessage(), file.getOriginalFilename());
                filesStatus.add(status);
            }
        }
            return filesStatus;
    }


    public ArrayList<TreeNode> treeNode(Integer projectID, String user) {
        ArrayList<SectionDepartments> sectionDepartments =
                new ArrayList<>(projectRepository.getOneById(projectID).getSectionsDepartments());
        sectionDepartments.sort(Comparator.comparing(SectionDepartments::getCode));
        ArrayList<TreeNode> treeNode = new ArrayList<>();
        for (SectionDepartments sd : sectionDepartments)
            treeNode.add(new TreeNode(sd.getCode(), sd.getExpandedIcon(), sd.getCollapsedIcon(), sd.getDepartmentsLikeTreeNode()));

        return treeNode;

    }


}
