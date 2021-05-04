package test.intdmp.core.service.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import test.intdmp.core.configuration.FileDirectoryConfiguration;
import test.intdmp.core.model.files._enum.UploadStatus;
import test.intdmp.core.service.fileManager._class.FileExistsException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileSystemRepository {

    @Autowired
    private FileDirectoryConfiguration fileDirectoryConfiguration;


    public FileSystemRepository() throws URISyntaxException {
    }

    public String save(byte[] content, String name) throws IOException {
        Path newFile = Paths.get(fileDirectoryConfiguration.saveDirectory() + "\\" + name);
        Files.createDirectories(newFile.getParent());

        if (Files.notExists(newFile)) {
            Files.write(newFile, content);
            return newFile.toAbsolutePath().toString();
        }
        else
            throw new FileExistsException(UploadStatus.FILE_EXIST);
    }

}
