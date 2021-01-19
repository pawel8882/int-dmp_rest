package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.Project;
import test.intdmp.core.model.department;
import test.intdmp.core.model.messages.*;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@Transactional
public class MessagesService {

    private EntityManager entityManager;
    public MessagesService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Header> getHeaders() { return getAll(); }


    public List<Header> getAll() {

        return entityManager.createQuery("SELECT a FROM Header a", Header.class).getResultList();
    }

}
