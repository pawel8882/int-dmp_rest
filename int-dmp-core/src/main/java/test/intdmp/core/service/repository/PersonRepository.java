package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.projects.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Person findByUsername(String name);
    Person findById(Long id);
    Person getOneById(Integer id);

}
