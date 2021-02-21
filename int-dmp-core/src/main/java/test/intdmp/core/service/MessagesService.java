package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.helpClass.*;
import test.intdmp.core.model.person.messages.*;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.messages.*;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.sortMessages.PaginatorFilter;
import test.intdmp.core.sortMessages.SortMessagesOutput;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@Transactional
public class MessagesService extends SortMessagesOutput {

    private EntityManager entityManager;

    public MessagesService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ParamDisplayMessages getReceivedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getUserReceivedMessages(projectId, user, paginator);
    }

    public ParamDisplayMessages getSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getUserSentMessages(projectId, user, paginator);
    }

    public List<SuggestPerson> getSuggestUsers(Integer projectId, String user) {
        return getSuggestions(projectId, user);
    }

    public List<CategoriesMessages> getUserCategories(String user) {
        return getCategories(user);
    }

    public Integer setNewMessage(NewMessage newMessage, Integer projectId, String user) {
        return setMessage(newMessage, projectId, user);
    }

    public DetailedMessage getDetailedMessage(Integer messageId, String user) {
        return getMessage(messageId, user);
    }

    public Integer setReplyMessage(Integer messageId, String user, NewMessage newMessage) {
        return setReplyMG(messageId, user, newMessage);
    }


    public List<Header> getAll() {

        return entityManager.createQuery("SELECT a FROM Header a", Header.class).getResultList();
    }

    public ParamDisplayMessages getUserReceivedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        if (!paginator.categories.isEmpty()) {
            Set<InformationOnlyMessages> informationOnlyMessages = new HashSet<>();
            Set<ReceivedMessages> messages = new HashSet<>();
            paginator.categories.forEach(e -> {
                        CategoriesMessages messagesFromCategory = entityManager.find(CategoriesMessages.class, e.id);
                        messages.addAll(messagesFromCategory.getReceivedMessages());
                        informationOnlyMessages.addAll(messagesFromCategory.getInformationOnlyMessages());
                    }
            );
            return GetSortedReceived(paginator, messages, informationOnlyMessages, projectId, person.getId());
        }
        Set<ReceivedMessages> messages = person.getReceivedMessages();
        Set<InformationOnlyMessages> informationOnlyMessages = person.getInformationOnlyMessages();
        return GetSortedReceived(paginator, messages, informationOnlyMessages, projectId, person.getId());
    }


    public ParamDisplayMessages getUserSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = entityManager.createQuery("SELECT p FROM Person p  WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        if (!paginator.categories.isEmpty()) {
            Set<ReceivedMessages> replyMessages = new HashSet<>();
            Set<DataMessages> messages = new HashSet<>();
            paginator.categories.forEach(e -> {
                        CategoriesMessages messagesFromCategory = entityManager.find(CategoriesMessages.class, e.id);
                        messages.addAll(messagesFromCategory.getDataMessages());
                        replyMessages.addAll(messagesFromCategory.getReceivedMessages());
                    }
            );
            return GetSortedSent(paginator, messages, replyMessages, projectId, person.getId());
        }
        Set<DataMessages> messages = person.getDataMessages();
        Set<ReceivedMessages> replyMessages = person.getReceivedMessages();
        return GetSortedSent(paginator, messages, replyMessages, projectId, person.getId());
    }

    public List<SuggestPerson> getSuggestions(Integer projectId, String user) {
        user.replace(" ", "");
        Set<PersonsProjects> PersonsProjects = entityManager.find(Project.class, projectId).getPersons();
        List<SuggestPerson> persons = new ArrayList<>();
        for (PersonsProjects e : PersonsProjects
        ) {
            if ((e.getPerson().getFirstName().toLowerCase().replace(" ", "") + e.getPerson().getLastName().toLowerCase().replace(" ", "")).startsWith(user))
                persons.add(new SuggestPerson(e.getPerson().getId(), e.getPerson().getFirstName() + " " + e.getPerson().getLastName()));
        }
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
        message.setTimestamp(new Timestamp(date.getTime()));
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

        Date date = new Date();
        DataMessages dataMessage = entityManager.getReference(DataMessages.class, dataMessageId);
        toPersons.forEach(e -> {
            ReceivedMessages data = new ReceivedMessages();
            data.setOpened(false);
            data.setPerson(entityManager.getReference(Person.class, e.id));
            data.setDataMessages(dataMessage);
            data.setTimestamp(new Timestamp(date.getTime()));
            entityManager.persist(data);
        });
        entityManager.flush();
    }

    private void setInformationOnlyMessages(Integer dataMessageId, List<SuggestPerson> dwPersons) {

        Date date = new Date();
        DataMessages dataMessage = entityManager.getReference(DataMessages.class, dataMessageId);
        dwPersons.forEach(e -> {
            InformationOnlyMessages data = new InformationOnlyMessages();
            data.setOpened(false);
            data.setPerson(entityManager.getReference(Person.class, e.id));
            data.setDataMessages(dataMessage);
            data.setTimestamp(new Timestamp(date.getTime()));
            entityManager.persist(data);
        });
        entityManager.flush();
    }

    private DetailedMessage getMessage(Integer messageId, String user) {

        DataMessages dataMessage = entityManager.find(DataMessages.class, messageId);
        String Header = dataMessage.getHeader().getTitle();
        Message message = dataMessage.getHeader().getMessage();
        SuggestPerson owner = new SuggestPerson(dataMessage.getPerson().getId(), dataMessage.getPerson().getFirstName() + " " + dataMessage.getPerson().getLastName());
        List<SuggestPerson> toPersons = new ArrayList<>(dataMessage.getPersons());
        List<SuggestPerson> dwPersons = new ArrayList<>(dataMessage.getPersonsDW());
        List<ReplyMessage> sorted = new ArrayList<ReplyMessage>();

        return new DetailedMessage(Header, message, owner, toPersons, dwPersons);


    }

    private Integer setReplyMG(Integer messageId, String user, NewMessage newMessage) {

        Date date = new Date();
        DataReplyMessages replyData = new DataReplyMessages();
        ReplyMessage reply = new ReplyMessage();

        Message message = entityManager.getReference(DataMessages.class, messageId).getHeader().getMessage();
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();

        reply.setContent(newMessage.content);
        reply.setTimestamp(new Timestamp(date.getTime()));
        reply.setMessage(message);
        replyData.setReplyMessage(reply);
        replyData.setDataMessages(message.getHeader().getDataMessages());
        replyData.setPerson(person);
        replyData.setUpdate(false);

        entityManager.persist(reply);
        entityManager.persist(replyData);

        DataMessages dataMessage = entityManager.getReference(DataMessages.class, messageId);
        if (person.getId() == dataMessage.getPerson().getId())
            updateSentAndReceivedMessagesOwnerReply(date, dataMessage, person);
        else {
            updateReceivedMessagesPersonInToPersons(date, dataMessage, person, newMessage);
        }

        return reply.getId();


    }

    private void updateSentAndReceivedMessagesOwnerReply(Date date, DataMessages dataMessage, Person person) {

        dataMessage.getInformationOnlyMessages().forEach(
                e -> {
                    e.setTimestamp(new Timestamp(date.getTime()));
                    e.setOpened(false);
                    entityManager.merge(e);
                }
        );
        dataMessage.getReceivedMessages().forEach(
                e -> {
                    if (person.getId() != e.getPerson().getId()) {
                        e.setTimestamp(new Timestamp(date.getTime()));
                        e.setOpened(false);
                        entityManager.merge(e);
                    }
                });
    }


    private void updateReceivedMessagesPersonInToPersons(Date date, DataMessages dataMessage, Person person, NewMessage newMessage) {
        dataMessage.getReceivedMessages().forEach(u ->
                newMessage.toPersons.forEach(e ->
                {
                    if (e.id == u.getPerson().getId() && e.id != person.getId()) {
                        u.setOpened(false);
                        u.setTimestamp(new Timestamp(date.getTime()));
                        entityManager.merge(u);
                    }
                }));
        if (!dataMessage.getReceivedMessages().stream().filter(e -> e.getPerson().getId().equals(dataMessage.getPerson().getId())).findFirst().isPresent()) {

            ReceivedMessages receivedMessage = new ReceivedMessages();
            receivedMessage.setTimestamp(new Timestamp(date.getTime()));
            receivedMessage.setOpened(false);
            receivedMessage.setPerson(entityManager.getReference(Person.class, dataMessage.getPerson().getId()));
            receivedMessage.setDataMessages(dataMessage);
            entityManager.persist(receivedMessage);
        }

    }

    public List<CategoriesMessages> getCategories(String user) {
        List<CategoriesMessages> categories = new ArrayList<>(entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult().getCategories());
        categories.sort(Comparator.comparing( e -> e.getCategory()));
        return categories;
    }

}




