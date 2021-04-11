package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.person.messages.ReceivedMessages;

@Repository
public interface ReceivedMessagesRepository extends JpaRepository<ReceivedMessages, Integer> {

    ReceivedMessages getOneById(Integer id);
    ReceivedMessages save(ReceivedMessages message);

}
