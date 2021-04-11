package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.person.messages.InformationOnlyMessages;

@Repository
public interface InformationOnlyMessagesRepository extends JpaRepository<InformationOnlyMessages, Integer> {

    InformationOnlyMessages getOneById(Integer id);
    InformationOnlyMessages save(InformationOnlyMessages message);

}
