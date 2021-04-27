package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.files.FileIndex;
import test.intdmp.core.model.person.messages.DataReplyMessages;

@Repository
public interface FileIndexRepository extends JpaRepository<FileIndex, Integer> {

    FileIndex getOneById(Integer id);
    FileIndex save(FileIndex fileIndex);
}
