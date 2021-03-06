package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.helpClass.*;
import test.intdmp.core.model.person.messages.*;
import test.intdmp.core.model.person.messages.SentMessages;
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
    private String defaultCategory = "Ogólny";

    public MessagesService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public ParamDisplayMessages getReceivedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getUserReceivedMessages(projectId, user, paginator);
    }

    public ParamDisplayMessages getReceivedPinnedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getUserPinnedReceivedMessages(projectId, user, paginator);
    }

    public ParamDisplayMessages getSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getUserSentMessages(projectId, user, paginator);
    }

    public ParamDisplayMessages getSentPinnedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getUserPinnedSentMessages(projectId, user, paginator);
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

    public DetailedMessage getDetailedMessage(Integer messageId, String user, Integer id, Character character) {
        return getMessage(messageId, user, id, character);
    }

    public Integer setReplyMessage(Integer messageId, String user, NewMessage newMessage, Integer id, Character character) {
        return setReplyMG(messageId, user, newMessage, id, character);
    }

    public Boolean changePinned(UpdateMessage updateMessage, Integer messageId, String user) { return setOrUnsetPinned(updateMessage, messageId, user); }


    public List<Header> getAll() {

        return entityManager.createQuery("SELECT a FROM Header a", Header.class).getResultList();
    }

    private CategoriesMessages getCategoryReference(Integer categoryId) {

        CategoriesMessages categor = entityManager.find(CategoriesMessages.class, categoryId);

        return categor;

    }

    private CategoriesMessages getCategoryReference(Integer personId, String category) {

        List<CategoriesMessages> categories = new ArrayList<>(entityManager.find(Person.class, personId).getCategories());
        CategoriesMessages categor = categories.stream().filter(e -> (e.getCategory().contains(category))).findFirst().orElseThrow();

        return categor;

    }

    @Transactional
    private Boolean setOrUnsetPinned(UpdateMessage updateMessage, Integer messageId, String user) {

        DataMessages dataMessage = entityManager.getReference(DataMessages.class, messageId);
        if(updateMessage.type.equals('A')) {return dataMessage.getSentMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }
        if(updateMessage.type.equals('B')) {return dataMessage.getReceivedMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }
        if(updateMessage.type.equals('C')) {return dataMessage.getInformationOnlyMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }

        return !updateMessage.pinned;

    }

    private Boolean findMessagePinnedAndSetAsOpened(Integer id, Character character) {

        /* A = sentMessage, B = receivedMessage, C = InformationOnly */
        if(character.equals('A')) {
            SentMessages sentMessage = findSentMessage(id);
            Boolean pinned = sentMessage.getInfo().getPinned();
            sentMessage.setOpened(true);
            entityManager.merge(sentMessage);
            return pinned;
        }
        if(character.equals('B')) {
            ReceivedMessages receivedMessage = findReceivedMessage(id);
            Boolean pinned = receivedMessage.getInfo().getPinned();
            receivedMessage.setOpened(true);
            entityManager.merge(receivedMessage);
            return pinned;
        }
        if(character.equals('C')) {
            InformationOnlyMessages infoMessage = findInformationOnlyMessage(id);
            Boolean pinned = infoMessage.getInfo().getPinned();
            infoMessage.setOpened(true);
            entityManager.merge(infoMessage);
            return pinned;
        }
        return false;
    }

    private SentMessages findSentMessage(Integer id) {
        /* A = sentMessage, B = receivedMessage, C = InformationOnly */
            SentMessages sentMessage = entityManager.getReference(SentMessages.class, id);
        return sentMessage;
    }

    private ReceivedMessages findReceivedMessage(Integer id) {
        /* A = sentMessage, B = receivedMessage, C = InformationOnly */
        ReceivedMessages receivedMessage = entityManager.getReference(ReceivedMessages.class, id);
        return receivedMessage;
    }

    private InformationOnlyMessages findInformationOnlyMessage(Integer id) {
        /* A = sentMessage, B = receivedMessage, C = InformationOnly */
        InformationOnlyMessages informationOnlyMessage = entityManager.getReference(InformationOnlyMessages.class, id);
        return informationOnlyMessage;
    }

    private void updateSentMessage(SentMessages sentMessage, Date date) {
        sentMessage.setTimestamp(new Timestamp(date.getTime()));
        entityManager.merge(sentMessage);
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


    public ParamDisplayMessages getUserPinnedReceivedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        if (!paginator.categories.isEmpty()) {
            Set<InformationOnlyMessages> informationOnlyMessages = new HashSet<>();
            Set<ReceivedMessages> messages = new HashSet<>();
            paginator.categories.forEach(e -> {
                        CategoriesMessages messagesFromCategory = entityManager.find(CategoriesMessages.class, e.id);
                        messages.addAll(messagesFromCategory.getPinnedReceivedMessages());
                        informationOnlyMessages.addAll(messagesFromCategory.getPinnedInformationOnlyMessages());
                    }
            );
            return GetSortedReceived(paginator, messages, informationOnlyMessages, projectId, person.getId());
        }
        Set<ReceivedMessages> messages = person.getPinnedReceivedMessages();
        Set<InformationOnlyMessages> informationOnlyMessages = person.getPinnedInformationOnlyMessages();
        return GetSortedReceived(paginator, messages, informationOnlyMessages, projectId, person.getId());
    }


    public ParamDisplayMessages getUserSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = entityManager.createQuery("SELECT p FROM Person p  WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        if (!paginator.categories.isEmpty()) {
            Set<SentMessages> messages = new HashSet<>();
            paginator.categories.forEach(e -> {
                        CategoriesMessages messagesFromCategory = entityManager.find(CategoriesMessages.class, e.id);
                        messages.addAll(messagesFromCategory.getSentMessages());
                    }
            );
            return GetSortedSent(paginator, messages, projectId, person.getId());
        }
        Set<SentMessages> messages = person.getSentMessages();
        return GetSortedSent(paginator, messages, projectId, person.getId());
    }

    public ParamDisplayMessages getUserPinnedSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = entityManager.createQuery("SELECT p FROM Person p  WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        if (!paginator.categories.isEmpty()) {
            Set<SentMessages> messages = new HashSet<>();
            paginator.categories.forEach(e -> {
                        CategoriesMessages messagesFromCategory = entityManager.find(CategoriesMessages.class, e.id);
                        messages.addAll(messagesFromCategory.getPinnedSentMessages());
                    }
            );
            return GetSortedSent(paginator, messages, projectId, person.getId());
        }
        Set<SentMessages> messages = person.getPinnedSentMessages();
        return GetSortedSent(paginator, messages, projectId, person.getId());
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
        entityManager.persist(header);

        DataMessages data = new DataMessages();
        SentMessages sentMessage = new SentMessages();
        data.setHeader(header);
        data.setProject(project);
        data.setPerson(person);
        entityManager.persist(data);

        InfoAboutMessages info = new InfoAboutMessages();
        info.setPinned(false);
        entityManager.persist(info);
        sentMessage.setCategoriesMessages(getCategoryReference(person.getId(), this.defaultCategory));
        sentMessage.setOpened(true);
        sentMessage.setPerson(person);
        sentMessage.setInfo(info);
        sentMessage.setDataMessages(data);
        sentMessage.setType();
        sentMessage.setTimestamp(new Timestamp(date.getTime()));
        entityManager.persist(sentMessage);

        Integer dataMessageId = data.getId();
        entityManager.flush();

        setReceivedMessages(dataMessageId, newMessage.toPersons, date);
        setInformationOnlyMessages(dataMessageId, newMessage.dwPersons, date);

        return dataMessageId;

    }

    private void setReceivedMessages(Integer dataMessageId, List<SuggestPerson> toPersons, Date date) {

        DataMessages dataMessage = entityManager.getReference(DataMessages.class, dataMessageId);
        toPersons.forEach(e -> {
            InfoAboutMessages info = new InfoAboutMessages();
            info.setPinned(false);
            entityManager.persist(info);
            ReceivedMessages data = new ReceivedMessages();
            data.setOpened(false);
            data.setPerson(entityManager.getReference(Person.class, e.id));
            data.setDataMessages(dataMessage);
            data.setTimestamp(new Timestamp(date.getTime()));
            data.setCategoriesMessages(getCategoryReference(e.id, this.defaultCategory));
            data.setInfo(info);
            data.setType();
            entityManager.persist(data);
        });
        entityManager.flush();
    }

    private void setInformationOnlyMessages(Integer dataMessageId, List<SuggestPerson> dwPersons, Date date) {

        DataMessages dataMessage = entityManager.getReference(DataMessages.class, dataMessageId);
        dwPersons.forEach(e -> {
            InfoAboutMessages info = new InfoAboutMessages();
            info.setPinned(false);
            entityManager.persist(info);
            InformationOnlyMessages data = new InformationOnlyMessages();
            data.setOpened(false);
            data.setPerson(entityManager.getReference(Person.class, e.id));
            data.setDataMessages(dataMessage);
            data.setTimestamp(new Timestamp(date.getTime()));
            data.setCategoriesMessages(getCategoryReference(e.id, this.defaultCategory));
            data.setInfo(info);
            data.setType();
            entityManager.persist(data);
        });
        entityManager.flush();
    }

    private DetailedMessage getMessage(Integer messageId, String user, Integer id, Character character) {

        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        DataMessages dataMessage = entityManager.find(DataMessages.class, messageId);
        String Header = dataMessage.getHeader().getTitle();
        Message message = dataMessage.getHeader().getMessage();
        SuggestPerson owner = new SuggestPerson(dataMessage.getPerson().getId(), dataMessage.getPerson().getFirstName() + " " + dataMessage.getPerson().getLastName());
        List<SuggestPerson> toPersons = new ArrayList<>(dataMessage.getPersonsDO());
        List<SuggestPerson> dwPersons = new ArrayList<>(dataMessage.getPersonsDW());
        Boolean pinned = findMessagePinnedAndSetAsOpened(id, character);

        return new DetailedMessage(Header, message, owner, toPersons, dwPersons, character, id, pinned);


    }

    private Integer setReplyMG(Integer messageId, String user, NewMessage newMessage, Integer id, Character character) {

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

        entityManager.persist(reply);
        entityManager.persist(replyData);


        DataMessages dataMessage = entityManager.getReference(DataMessages.class, messageId);
        trySetNewSentMessageOrElseCreateNew(person, dataMessage, id, date);


        if (person.getId() == dataMessage.getPerson().getId())
            updateSentAndReceivedMessagesOwnerReply(date, dataMessage, person);
        else {
            updateReceivedMessagesPersonInToPersons(date, dataMessage, person, newMessage);
        }

        return reply.getId();


    }

    private void trySetNewSentMessageOrElseCreateNew(Person person, DataMessages dataMessage, Integer id, Date date) {

        Boolean isPresent = false;
        try { isPresent = dataMessage.getSentMessages().stream().filter(e -> e.getPerson().getId().equals(person.getId())).findFirst()
                .map(u -> { updateSentMessage(u, date); return u; })
                .isPresent(); }
        catch (Exception e) {
        }
        if (!isPresent)
            createNewSentMessage(date, dataMessage, person, id);
    }

    private SentMessages createNewSentMessage(Date date, DataMessages dataMessage, Person person, Integer id) {

        SentMessages sentMessage = new SentMessages();
        sentMessage.setTimestamp(new Timestamp(date.getTime()));
        sentMessage.setOpened(true);
        sentMessage.setDataMessages(dataMessage);
        sentMessage.setPerson(person);
        sentMessage.setInfo(findReceivedMessage(id).getInfo());
        sentMessage.setType();
        sentMessage.setCategoriesMessages(getCategoryReference(person.getId(), defaultCategory));
        entityManager.persist(sentMessage);

        return sentMessage;

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
            receivedMessage.setCategoriesMessages(getCategoryReference(dataMessage.getPerson().getId(), defaultCategory));
            receivedMessage.setType();
            dataMessage.getSentMessages().stream().filter(e -> e.getPerson().getId().equals(dataMessage.getPerson().getId())).findFirst().map(u -> { receivedMessage.setInfo(u.getInfo()); return u; });
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




