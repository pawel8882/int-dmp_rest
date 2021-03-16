package test.intdmp.core.service.messages;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.person.messages.*;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.service.messages._class.NewMessage;
import test.intdmp.core.service.messages._class.UpdateMessage;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ModifyMessages {

    private EntityManager entityManager;
    private final String defaultCategory = "Ogólny";

    public ModifyMessages(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CategoriesMessages getCategoryReference(Integer personId, String category) {

        List<CategoriesMessages> categories = new ArrayList<>(entityManager.find(Person.class, personId).getCategories());
        CategoriesMessages oneCategory = categories.stream().filter(e -> (e.getCategory().contains(category))).findFirst().orElseThrow();

        return oneCategory;

    }

    public SentMessages findSentMessage(Integer id) {
        /* A = sentMessage, B = receivedMessage, C = InformationOnly */
        SentMessages sentMessage = entityManager.getReference(SentMessages.class, id);
        return sentMessage;
    }

    public ReceivedMessages findReceivedMessage(Integer id) {
        /* A = sentMessage, B = receivedMessage, C = InformationOnly */
        ReceivedMessages receivedMessage = entityManager.getReference(ReceivedMessages.class, id);
        return receivedMessage;
    }

    public InformationOnlyMessages findInformationOnlyMessage(Integer id) {
        /* A = sentMessage, B = receivedMessage, C = InformationOnly */
        InformationOnlyMessages informationOnlyMessage = entityManager.getReference(InformationOnlyMessages.class, id);
        return informationOnlyMessage;
    }

    @Transactional
    public Boolean setOrUnsetPinned(UpdateMessage updateMessage, Integer messageId, String user) {

        DataMessages dataMessage = entityManager.getReference(DataMessages.class, messageId);
        if(updateMessage.type.equals('A')) {return dataMessage.getSentMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }
        if(updateMessage.type.equals('B')) {return dataMessage.getReceivedMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }
        if(updateMessage.type.equals('C')) {return dataMessage.getInformationOnlyMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }

        return !updateMessage.pinned;

    }

    public Boolean findMessagePinnedAndSetAsOpened(Integer id, Character character) {

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

    public void updateSentMessage(SentMessages sentMessage, Date date) {
        sentMessage.setTimestamp(new Timestamp(date.getTime()));
        entityManager.merge(sentMessage);
    }

    public void updateSentAndReceivedMessagesOwnerReply(Date date, DataMessages dataMessage, Person person) {

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

    public void updateReceivedMessagesPersonInToPersons(Date date, DataMessages dataMessage, Person person, NewMessage newMessage) {
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

}
