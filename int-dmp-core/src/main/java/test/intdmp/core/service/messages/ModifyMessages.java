package test.intdmp.core.service.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.person.messages.*;
import test.intdmp.core.service.messages._class.UpdateMessage;
import test.intdmp.core.service.repository.*;
import test.intdmp.core.model.person.messages._enum.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Component
public class ModifyMessages {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private DataMessagesRepository dataMessagesRepository;
    @Autowired
    private SentMessagesRepository sentMessagesRepository;
    @Autowired
    private ReceivedMessagesRepository receivedMessagesRepository;
    @Autowired
    private InformationOnlyMessagesRepository informationOnlyMessagesRepository;

    private final String defaultCategory = "Ogólny";

    public CategoriesMessages userCategoryReference(Integer personId, String category) {

        List<CategoriesMessages> categories = new ArrayList<>(personRepository.findById(personId).get().getCategories());
        CategoriesMessages oneCategory = categories.stream().filter(e -> (e.getCategory().contains(category))).findFirst().orElseThrow();

        return oneCategory;

    }

    @Transactional
    public Boolean setOrUnsetPinned(UpdateMessage updateMessage, Integer messageId, String user) {

        DataMessages dataMessage = dataMessagesRepository.getOneById(messageId);
        if(updateMessage.messageType.equals(MessageType.SENT)) {return dataMessage.getSentMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }
        if(updateMessage.messageType.equals(MessageType.RECEIVED)) {return dataMessage.getReceivedMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }
        if(updateMessage.messageType.equals(MessageType.INFORMATION)) {return dataMessage.getInformationOnlyMessages().stream().filter(u -> u.getId().equals(updateMessage.id))
                .findFirst().map(e -> { e.getInfo().setPinned(updateMessage.pinned); return e;}).get().getInfo().getPinned(); }

        return !updateMessage.pinned;

    }


    public void markMessageAsOpened(Integer id, MessageType messageType) {

        if(messageType.equals(MessageType.SENT)) {
            SentMessages sentMessage = sentMessagesRepository.getOneById(id);
            sentMessage.setOpened(true);
            sentMessagesRepository.save(sentMessage);
        }
        if(messageType.equals(MessageType.RECEIVED)) {
            ReceivedMessages receivedMessage = receivedMessagesRepository.getOneById(id);
            receivedMessage.setOpened(true);
            receivedMessagesRepository.save(receivedMessage);
        }
        if(messageType.equals(MessageType.INFORMATION)) {
            InformationOnlyMessages infoMessage = informationOnlyMessagesRepository.getOneById(id);
            infoMessage.setOpened(true);
            informationOnlyMessagesRepository.save(infoMessage);
        }
    }


}
