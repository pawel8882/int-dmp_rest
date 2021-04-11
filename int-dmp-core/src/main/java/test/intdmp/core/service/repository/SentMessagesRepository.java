package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.person.messages.SentMessages;

@Repository
public interface SentMessagesRepository extends JpaRepository<SentMessages, Integer> {

    SentMessages getOneById(Integer id);
    SentMessages save(SentMessages message);

}
