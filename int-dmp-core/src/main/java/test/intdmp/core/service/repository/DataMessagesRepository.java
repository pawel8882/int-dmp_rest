package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.person.messages.DataMessages;

@Repository
public interface DataMessagesRepository extends JpaRepository<DataMessages, Integer> {

    DataMessages findById(Long id);
    DataMessages getOneById(Integer id);
    DataMessages save(DataMessages message);

}


