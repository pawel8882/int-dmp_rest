package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.person.messages.DataReplyMessages;

@Repository
public interface DataReplyMessagesRepository extends JpaRepository<DataReplyMessages, Integer> {

    DataReplyMessages getOneById(Integer id);
    DataReplyMessages save(DataReplyMessages dataMessage);

}
