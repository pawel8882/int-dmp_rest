package test.intdmp.core.service.repository;

import org.springframework.stereotype.Repository;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Repository
public class FileSystemRepository {

    String resources_dir = FileSystemRepository.class.getResource("/")
            .getPath();

    public String save(byte[] content, String name) throws Exception {
        Path newFile = Paths.get(resources_dir + name);
        Files.createDirectories(newFile.getParent());

        Files.write(newFile, content);

        return newFile.toAbsolutePath().toString();
    }

}
