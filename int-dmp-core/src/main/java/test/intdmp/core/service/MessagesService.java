package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.helpClass.NewMessage;
import test.intdmp.core.helpClass.SuggestPerson;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.messages.*;
import test.intdmp.core.model.person.messages.InformationOnlyMessages;
import test.intdmp.core.model.person.messages.DataMessages;
import test.intdmp.core.model.person.messages.ReceivedMessages;
import test.intdmp.core.model.projects.PersonsProjects;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@Transactional
public class MessagesService {

    private EntityManager entityManager;
    public MessagesService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ReceivedMessages> getHeaders(Integer projectId, String user, Integer MinRange, Integer MaxRange) { return getUserReceivedHeaders(projectId, user, MinRange, MaxRange); }

    public List<DataMessages> getSentHeaders(Integer projectId, String user, Integer MinRange, Integer MaxRange) { return getUserSentHeaders(projectId, user, MinRange, MaxRange); }

    public List<SuggestPerson> getSuggestUsers(Integer projectId, String user) { return getSuggestions(projectId, user); }

    public Integer setNewMessage(NewMessage newMessage, Integer projectId, String user) { return setMessage(newMessage, projectId, user); }


    public List<Header> getAll() {

        return entityManager.createQuery("SELECT a FROM Header a", Header.class).getResultList();
    }

    public List<ReceivedMessages> getUserReceivedHeaders(Integer projectId, String user, Integer MinRange, Integer MaxRange) {
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        Set<ReceivedMessages> messages = entityManager.find(Person.class, person.getId()).getReceivedMessages();
        return GetSorted(MinRange, MaxRange, messages, projectId);
    }

    public List<DataMessages> getUserSentHeaders(Integer projectId, String user, Integer MinRange, Integer MaxRange) {
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        Set<DataMessages> messages = entityManager.find(Person.class, person.getId()).getDataMessages();
        return GetSortedSent(MinRange, MaxRange, messages, projectId);
    }

  private List<ReceivedMessages> GetSorted(Integer MinRange, Integer MaxRange, Set<ReceivedMessages> UnSorted, Integer projectId) {
        List<ReceivedMessages> ToSorted = new ArrayList<>(UnSorted);
        ToSorted.removeIf(e -> (e.getDataMessages().getProject().getId() != projectId));
        if (ToSorted.size() < MaxRange)
        { MaxRange = ToSorted.size(); }
        if (ToSorted.size() < MinRange)
        { MinRange = ToSorted.size() - 1; }
        ToSorted.sort(Comparator.comparing((ReceivedMessages e) -> e.getDataMessages().getHeader().getTimestamp()).reversed());
        return ToSorted.subList(MinRange, MaxRange);
    }

    private List<DataMessages> GetSortedSent(Integer MinRange, Integer MaxRange, Set<DataMessages> UnSorted, Integer projectId) {
        List<DataMessages> ToSorted = new ArrayList<>(UnSorted);
        ToSorted.removeIf(e -> (e.getProject().getId() != projectId));
        if (ToSorted.size() < MaxRange)
        { MaxRange = ToSorted.size(); }
        if (ToSorted.size() < MinRange)
        { MinRange = ToSorted.size() - 1; }
        ToSorted.sort(Comparator.comparing((DataMessages e) -> e.getHeader().getTimestamp()).reversed());
        return ToSorted.subList(MinRange, MaxRange);
    }

    public List<SuggestPerson> getSuggestions(Integer projectId, String user) {
        user.replace(" ", "");
        Set<PersonsProjects> PersonsProjects = entityManager.find(Project.class, projectId).getPersons();
        List<SuggestPerson> persons = new ArrayList<>();
        for (PersonsProjects e: PersonsProjects
             ) {
            if ((e.getPerson().getFirstName().toLowerCase().replace(" ", "") + e.getPerson().getLastName().toLowerCase().replace(" ", "")).startsWith(user))
                persons.add(new SuggestPerson(e.getPerson().getId(), e.getPerson().getFirstName() + " " + e.getPerson().getLastName())); }
        persons.sort(Comparator.comparing((SuggestPerson e) -> e.personName));
        return persons;
    }

    private Integer setMessage(NewMessage newMessage, Integer projectId, String user) {
        Date date = new Date();
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        Project project = entityManager.getReference(Project.class, projectId);
        Header header = new Header();
        Message message = new Message();
        message.setContent(newMessage.content);
        header.setTitle(newMessage.header);
        header.setMessage(message);
        header.setTimestamp(new Timestamp(date.getTime()));
        entityManager.persist(header);

        DataMessages data = new DataMessages();
        data.setOpened(true);
        data.setHeader(header);
        data.setProject(project);
        data.setPerson(person);

        entityManager.persist(data);
        Integer dataMessageId = data.getId();
        entityManager.flush();

        setReceivedMessages(dataMessageId, newMessage.toPersons);
        setInformationOnlyMessages(dataMessageId, newMessage.dwPersons);

        return dataMessageId;

    }

    private void setReceivedMessages(Integer dataMessageId, List<SuggestPerson> toPersons) {

        DataMessages dataMessage = entityManager.getReference(DataMessages.class, dataMessageId);
        toPersons.forEach(e -> {
            ReceivedMessages data = new ReceivedMessages();
            data.setOpened(false);
            data.setPerson(entityManager.getReference(Person.class, e.id));
            data.setDataMessages(dataMessage);
            entityManager.persist(data);
        });
        entityManager.flush();
    }

    private void setInformationOnlyMessages(Integer dataMessageId, List<SuggestPerson> dwPersons) {

        DataMessages dataMessage = entityManager.getReference(DataMessages.class, dataMessageId);
        dwPersons.forEach(e -> {
            InformationOnlyMessages data = new InformationOnlyMessages();
            data.setOpened(false);
            data.setPerson(entityManager.getReference(Person.class, e.id));
            data.setDataMessages(dataMessage);
            entityManager.persist(data);
        });
        entityManager.flush();
    }



}
