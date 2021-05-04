package test.intdmp.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import test.intdmp.core.service.FileService;
import test.intdmp.core.service.fileManager._class.FileInfo;
import test.intdmp.core.service.fileManager._class.FileStatus;
import test.intdmp.core.service.fileManager._class.TreeNode;
import test.intdmp.core.service.messages._class.SuggestPerson;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @RequestMapping(value = "/save", consumes = {"multipart/form-data", "application/json"},method = RequestMethod.POST)
    public ArrayList<FileStatus> saveFile(@RequestPart("fileInfo") String fileInfo, @RequestPart("file") MultipartFile files[]) throws Exception {
        FileInfo info = new ObjectMapper().readValue(fileInfo, FileInfo.class);
        return fileService.saveFileAndFileIndex(files, info);
    }

    @RequestMapping(value = "/{projectId}/files", method = RequestMethod.GET)
    public ArrayList<TreeNode> getTreeNode(@PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return fileService.treeNode(projectId, user);
    }

}
