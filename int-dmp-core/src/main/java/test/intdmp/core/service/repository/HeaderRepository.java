package test.intdmp.core.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import test.intdmp.core.model.messages.Header;

@Repository
public interface HeaderRepository extends JpaRepository<Header, Integer> {

    Header findById(Long id);
    Header getOneById(Integer id);
    Header save(Header header);

}



