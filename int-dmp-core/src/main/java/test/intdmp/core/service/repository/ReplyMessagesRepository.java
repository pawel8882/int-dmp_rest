package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.messages.ReplyMessage;

@Repository
public interface ReplyMessagesRepository extends JpaRepository<ReplyMessage, Integer> {

    ReplyMessage getOneById(Integer id);
    ReplyMessage save(ReplyMessage replyMessage);

}
