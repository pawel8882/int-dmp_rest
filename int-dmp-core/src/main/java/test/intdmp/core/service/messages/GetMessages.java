package test.intdmp.core.service.messages;
import org.springframework.stereotype.Service;
import test.intdmp.core.model.messages.Message;
import test.intdmp.core.model.person.messages.*;

import test.intdmp.core.model.projects.Person;
import test.intdmp.core.service.messages._class.DetailedMessage;
import test.intdmp.core.service.messages._class.NumberAndListDisplayMessages;
import test.intdmp.core.service.messages._class.PaginatorFilter;
import test.intdmp.core.service.messages._class.SuggestPerson;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class GetMessages {

    private EntityManager entityManager;
    private SortMessages sortMessages;
    private ModifyMessages modifyMessages;

    public GetMessages(EntityManager entityManager, SortMessages sortMessages, ModifyMessages modifyMessages) {
        this.sortMessages = sortMessages;
        this.modifyMessages = modifyMessages;
        this.entityManager = entityManager;
    }

    public NumberAndListDisplayMessages getUserReceivedMessages(Integer projectId, String user, PaginatorFilter paginator) {
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
            return sortMessages.GetSortedReceived(paginator, messages, informationOnlyMessages, projectId, person.getId());
        }
        Set<ReceivedMessages> messages = person.getReceivedMessages();
        Set<InformationOnlyMessages> informationOnlyMessages = person.getInformationOnlyMessages();
        return sortMessages.GetSortedReceived(paginator, messages, informationOnlyMessages, projectId, person.getId());
    }


    public NumberAndListDisplayMessages getUserPinnedReceivedMessages(Integer projectId, String user, PaginatorFilter paginator) {
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
            return sortMessages.GetSortedReceived(paginator, messages, informationOnlyMessages, projectId, person.getId());
        }
        Set<ReceivedMessages> messages = person.getPinnedReceivedMessages();
        Set<InformationOnlyMessages> informationOnlyMessages = person.getPinnedInformationOnlyMessages();
        return sortMessages.GetSortedReceived(paginator, messages, informationOnlyMessages, projectId, person.getId());
    }

    public NumberAndListDisplayMessages getUserSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = entityManager.createQuery("SELECT p FROM Person p  WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        if (!paginator.categories.isEmpty()) {
            Set<SentMessages> messages = new HashSet<>();
            paginator.categories.forEach(e -> {
                        CategoriesMessages messagesFromCategory = entityManager.find(CategoriesMessages.class, e.id);
                        messages.addAll(messagesFromCategory.getSentMessages());
                    }
            );
            return sortMessages.GetSortedSent(paginator, messages, projectId, person.getId());
        }
        Set<SentMessages> messages = person.getSentMessages();
        return sortMessages.GetSortedSent(paginator, messages, projectId, person.getId());
    }

    public NumberAndListDisplayMessages getUserPinnedSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        Person person = entityManager.createQuery("SELECT p FROM Person p  WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        if (!paginator.categories.isEmpty()) {
            Set<SentMessages> messages = new HashSet<>();
            paginator.categories.forEach(e -> {
                        CategoriesMessages messagesFromCategory = entityManager.find(CategoriesMessages.class, e.id);
                        messages.addAll(messagesFromCategory.getPinnedSentMessages());
                    }
            );
            return sortMessages.GetSortedSent(paginator, messages, projectId, person.getId());
        }
        Set<SentMessages> messages = person.getPinnedSentMessages();
        return sortMessages.GetSortedSent(paginator, messages, projectId, person.getId());
    }

    public DetailedMessage getMessage(Integer messageId, String user, Integer id, Character character) {

        Person person = entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult();
        DataMessages dataMessage = entityManager.find(DataMessages.class, messageId);
        String Header = dataMessage.getHeader().getTitle();
        Message message = dataMessage.getHeader().getMessage();
        SuggestPerson owner = new SuggestPerson(dataMessage.getPerson().getId(), dataMessage.getPerson().getFirstName() + " " + dataMessage.getPerson().getLastName());
        List<SuggestPerson> toPersons = new ArrayList<>(dataMessage.getPersonsDO());
        List<SuggestPerson> dwPersons = new ArrayList<>(dataMessage.getPersonsDW());
        Boolean pinned = modifyMessages.findMessagePinnedAndSetAsOpened(id, character);

        return new DetailedMessage(Header, message, owner, toPersons, dwPersons, character, id, pinned);


    }

}
