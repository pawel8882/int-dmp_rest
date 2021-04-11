package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.projects.PersonsProjects;

@Repository
public interface PersonsProjectsRepository extends JpaRepository<PersonsProjects, Integer> {

    PersonsProjects save(PersonsProjects personsProjects);

}
