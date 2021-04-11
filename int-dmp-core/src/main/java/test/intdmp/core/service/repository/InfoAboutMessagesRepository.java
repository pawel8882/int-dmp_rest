package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.messages.Header;
import test.intdmp.core.model.person.messages.InfoAboutMessages;

@Repository
public interface InfoAboutMessagesRepository extends JpaRepository<InfoAboutMessages, Integer> {

    InfoAboutMessages findById(Long id);
    InfoAboutMessages getOneById(Integer id);
    InfoAboutMessages save(InfoAboutMessages info);

}



