package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.person.messages.CategoriesMessages;

@Repository
public interface CategoriesMessagesRepository extends JpaRepository<CategoriesMessages, Integer> {

    CategoriesMessages findById(Long id);

}
