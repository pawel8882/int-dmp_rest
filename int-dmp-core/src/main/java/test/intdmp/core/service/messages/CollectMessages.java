package test.intdmp.core.service.messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.intdmp.core.model.messages.Message;
import test.intdmp.core.model.person.messages.*;

import test.intdmp.core.model.person.messages._enum.MessageType;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.service.messages._class.DetailedMessage;
import test.intdmp.core.service.messages._class.NumberAndListDisplayMessages;
import test.intdmp.core.service.messages._class.PaginatorFilter;
import test.intdmp.core.service.messages._class.SuggestPerson;
import test.intdmp.core.service.repository.*;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class CollectMessages {

    @Autowired
    private SortMessages sortMessages;
    @Autowired
    private ModifyMessages modifyMessages;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private DataMessagesRepository dataMessagesRepository;
    @Autowired
    private CategoriesMessagesRepository categoriesMessagesRepository;
    @Autowired
    private SentMessagesRepository sentMessagesRepository;
    @Autowired
    private ReceivedMessagesRepository receivedMessagesRepository;
    @Autowired
    private InformationOnlyMessagesRepository informationOnlyMessagesRepository;

    public NumberAndListDisplayMessages userReceivedAndInformationOnlyMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = personRepository.findByUsername(user);
        if (!paginator.categories.isEmpty()) {
            Set<InformationOnlyMessages> informationOnlyMessages = userInformationOnlyMessages(person, paginator);
            Set<ReceivedMessages> messages = userReceivedMessages(person, paginator);
            return sortMessages.sortReceivedMessages(paginator, messages, informationOnlyMessages, projectId, person.getId());
        }
        Set<ReceivedMessages> messages = userReceivedMessages(person);
        Set<InformationOnlyMessages> informationOnlyMessages = userInformationOnlyMessages(person);
        return sortMessages.sortReceivedMessages(paginator, messages, informationOnlyMessages, projectId, person.getId());
    }


    public NumberAndListDisplayMessages userPinnedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = personRepository.findByUsername(user);
        if (!paginator.categories.isEmpty()) {
            Set<InformationOnlyMessages> informationOnlyMessages = userPinnedInformationOnlyMessages(person, paginator);
            Set<ReceivedMessages> messages = userPinnedReceivedMessages(person, paginator);
            Set<SentMessages> sentMessages = userPinnedSentMessages(person, paginator);
            return sortMessages.sortPinnedMessages(paginator, sentMessages, messages, informationOnlyMessages, projectId, person.getId());
        }
        Set<ReceivedMessages> messages = userPinnedReceivedMessages(person);
        Set<InformationOnlyMessages> informationOnlyMessages = userPinnedInformationOnlyMessages(person);
        Set<SentMessages> sentMessages = userPinnedSentMessages(person);
        return sortMessages.sortPinnedMessages(paginator, sentMessages, messages, informationOnlyMessages, projectId, person.getId());
    }

    public NumberAndListDisplayMessages userSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = personRepository.findByUsername(user);
        if (!paginator.categories.isEmpty()) {
            Set<SentMessages> messages = userSentMessages(person, paginator);
            return sortMessages.sortSentMessages(paginator, messages, projectId, person.getId());
        }
        Set<SentMessages> messages = userSentMessages(person);
        return sortMessages.sortSentMessages(paginator, messages, projectId, person.getId());
    }

    public DetailedMessage detailedMessage(Integer messageId, String user, Integer id, MessageType messageType) {

        DataMessages dataMessage = dataMessagesRepository.findById(messageId).get();
        String Header = dataMessage.getHeader().getTitle();
        Message message = dataMessage.getHeader().getMessage();
        SuggestPerson owner = new SuggestPerson(dataMessage.getPerson().getId(),
                dataMessage.getPerson().getFirstName() + " " + dataMessage.getPerson().getLastName());
        List<SuggestPerson> toPersons = new ArrayList<>(dataMessage.getPersonsDO());
        List<SuggestPerson> dwPersons = new ArrayList<>(dataMessage.getPersonsDW());
        Boolean pinned = checkIfMessageIsPinned(id, messageType);

        modifyMessages.markMessageAsOpened(id, messageType);

        return new DetailedMessage(Header, message, owner, toPersons, dwPersons, messageType, id, pinned);

    }

    private Set<ReceivedMessages> userReceivedMessages(Person person) {

        Set<ReceivedMessages> messages = person.getReceivedMessages();

        return messages;
    }

    private Set<ReceivedMessages> userReceivedMessages(Person person, PaginatorFilter paginator) {

        Set<ReceivedMessages> messages = new HashSet<>();
        paginator.categories.forEach(e -> {
                    CategoriesMessages messagesFromCategory = categoriesMessagesRepository.findById(e.id).get();
                    messages.addAll(messagesFromCategory.getReceivedMessages());
                }
        );

        return messages;
    }

    private Set<ReceivedMessages> userPinnedReceivedMessages(Person person) {

        Set<ReceivedMessages> messages = person.getPinnedReceivedMessages();

        return messages;
    }

    private Set<ReceivedMessages> userPinnedReceivedMessages(Person person, PaginatorFilter paginator) {

        Set<ReceivedMessages> messages = new HashSet<>();
        paginator.categories.forEach(e -> {
                    CategoriesMessages messagesFromCategory = categoriesMessagesRepository.findById(e.id).get();
                    messages.addAll(messagesFromCategory.getPinnedReceivedMessages());
                }
        );

        return messages;
    }

    private Set<InformationOnlyMessages> userInformationOnlyMessages(Person person) {

        Set<InformationOnlyMessages> messages = person.getInformationOnlyMessages();

        return messages;
    }

    private Set<InformationOnlyMessages> userInformationOnlyMessages(Person person, PaginatorFilter paginator) {

        Set<InformationOnlyMessages> messages = new HashSet<>();
        paginator.categories.forEach(e -> {
                    CategoriesMessages messagesFromCategory = categoriesMessagesRepository.findById(e.id).get();
                    messages.addAll(messagesFromCategory.getInformationOnlyMessages());
                }
        );

        return messages;
    }

    private Set<InformationOnlyMessages> userPinnedInformationOnlyMessages(Person person) {

        Set<InformationOnlyMessages> messages = person.getPinnedInformationOnlyMessages();

        return messages;
    }

    private Set<InformationOnlyMessages> userPinnedInformationOnlyMessages(Person person, PaginatorFilter paginator) {

        Set<InformationOnlyMessages> messages = new HashSet<>();
        paginator.categories.forEach(e -> {
                    CategoriesMessages messagesFromCategory = categoriesMessagesRepository.findById(e.id).get();
                    messages.addAll(messagesFromCategory.getPinnedInformationOnlyMessages());
                }
        );

        return messages;
    }

    private Set<SentMessages> userSentMessages(Person person) {

        Set<SentMessages> messages = person.getSentMessages();

        return messages;
    }

    private Set<SentMessages> userSentMessages(Person person, PaginatorFilter paginator) {

        Set<SentMessages> messages = new HashSet<>();
        paginator.categories.forEach(e -> {
                    CategoriesMessages messagesFromCategory = categoriesMessagesRepository.findById(e.id).get();
                    messages.addAll(messagesFromCategory.getSentMessages());
                }
        );

        return messages;
    }

    private Set<SentMessages> userPinnedSentMessages(Person person) {

        Set<SentMessages> messages = person.getPinnedSentMessages();

        return messages;
    }

    private Set<SentMessages> userPinnedSentMessages(Person person, PaginatorFilter paginator) {

        Set<SentMessages> messages = new HashSet<>();
        paginator.categories.forEach(e -> {
                    CategoriesMessages messagesFromCategory = categoriesMessagesRepository.findById(e.id).get();
                    messages.addAll(messagesFromCategory.getPinnedSentMessages());
                }
        );

        return messages;
    }

    private Boolean checkIfMessageIsPinned(Integer id, MessageType messageType) {

        if(messageType.equals(MessageType.SENT)) {
            Boolean pinned = sentMessagesRepository.getOneById(id).getInfo().getPinned();
            return pinned;
        }
        if(messageType.equals(MessageType.RECEIVED)) {
            Boolean pinned = receivedMessagesRepository.getOneById(id).getInfo().getPinned();
            return pinned;
        }
        if(messageType.equals(MessageType.INFORMATION)) {
            Boolean pinned  = informationOnlyMessagesRepository.getOneById(id).getInfo().getPinned();
            return pinned;
        }
        return false;
    }

}
