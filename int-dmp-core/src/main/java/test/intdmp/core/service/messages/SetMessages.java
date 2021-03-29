package test.intdmp.core.service.messages;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import test.intdmp.core.model.messages.Header;
import test.intdmp.core.model.messages.Message;
import test.intdmp.core.model.messages.ReplyMessage;
import test.intdmp.core.model.person.messages.*;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.service.messages._class.NewMessage;
import test.intdmp.core.service.messages._class.SuggestPerson;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

@Component
public class SetMessages {

    private EntityManager entityManager;
    private ModifyMessages modifyMessages;
    private final String defaultCategory = "Ogólny";

    public SetMessages(EntityManager entityManager, ModifyMessages modifyMessages) {
        this.modifyMessages = modifyMessages;
        this.entityManager = entityManager;
    }

    public Integer setMessage(NewMessage newMessage, Integer projectId, String user) {
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
        sentMessage.setCategoriesMessages(modifyMessages.getCategoryReference(person.getId(), this.defaultCategory));
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

    public void setReceivedMessages(Integer dataMessageId, List<SuggestPerson> toPersons, Date date) {

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
            data.setCategoriesMessages(modifyMessages.getCategoryReference(e.id, this.defaultCategory));
            data.setInfo(info);
            data.setType();
            entityManager.persist(data);
        });
        entityManager.flush();
    }

    public void setInformationOnlyMessages(Integer dataMessageId, List<SuggestPerson> dwPersons, Date date) {

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
            data.setCategoriesMessages(modifyMessages.getCategoryReference(e.id, this.defaultCategory));
            data.setInfo(info);
            data.setType();
            entityManager.persist(data);
        });
        entityManager.flush();
    }

    public Integer setReplyMG(Integer messageId, String user, NewMessage newMessage, Integer id, Character character) {

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
        sentMessage.setInfo(modifyMessages.findReceivedMessage(id).getInfo());
        sentMessage.setType();
        sentMessage.setCategoriesMessages(modifyMessages.getCategoryReference(person.getId(), defaultCategory));
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
            receivedMessage.setCategoriesMessages(modifyMessages.getCategoryReference(dataMessage.getPerson().getId(), defaultCategory));
            receivedMessage.setType();
            dataMessage.getSentMessages().stream().filter(e -> e.getPerson().getId().equals(dataMessage.getPerson().getId())).findFirst().map(u -> { receivedMessage.setInfo(u.getInfo()); return u; });
            entityManager.persist(receivedMessage);
        }

    }

    public List<CategoriesMessages> getCategories(String user) {
        List<CategoriesMessages> categories = new ArrayList<>(entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult().getCategories());
        categories.sort(Comparator.comparing(e -> e.getCategory()));
        return categories;
    }

    private void updateSentMessage(SentMessages sentMessage, Date date) {
        sentMessage.setTimestamp(new Timestamp(date.getTime()));
        entityManager.merge(sentMessage);
    }

}
