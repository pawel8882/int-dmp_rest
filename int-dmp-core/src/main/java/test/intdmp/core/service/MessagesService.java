package test.intdmp.core.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.person.messages.*;
import test.intdmp.core.model.person.messages.SentMessages;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.model.messages.*;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.service.messages.GetMessages;
import test.intdmp.core.service.messages.ModifyMessages;
import test.intdmp.core.service.messages.SetMessages;
import test.intdmp.core.service.messages._class.*;

import java.sql.Timestamp;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@Transactional
public class MessagesService{

    private EntityManager entityManager;
    private SetMessages setMessages;
    private GetMessages getMessages;
    private ModifyMessages modifyMessages;

    public MessagesService(EntityManager entityManager, SetMessages setMessages, GetMessages getMessages, ModifyMessages modifyMessages) {

        this.setMessages = setMessages;
        this.getMessages = getMessages;
        this.modifyMessages = modifyMessages;
        this.entityManager = entityManager;
    }

    public NumberAndListDisplayMessages getReceivedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getMessages.getUserReceivedMessages(projectId, user, paginator);
    }

    public NumberAndListDisplayMessages getReceivedPinnedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getMessages.getUserPinnedReceivedMessages(projectId, user, paginator);
    }

    public NumberAndListDisplayMessages getSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getMessages.getUserSentMessages(projectId, user, paginator);
    }

    public NumberAndListDisplayMessages getSentPinnedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return getMessages.getUserPinnedSentMessages(projectId, user, paginator);
    }

    public List<SuggestPerson> getSuggestUsers(Integer projectId, String user) {
        return getSuggestions(projectId, user);
    }

    public List<CategoriesMessages> getUserCategories(String user) {
        return getCategories(user);
    }

    public Integer setNewMessage(NewMessage newMessage, Integer projectId, String user) {
        return setMessages.setMessage(newMessage, projectId, user);
    }

    public DetailedMessage getDetailedMessage(Integer messageId, String user, Integer id, Character character) {
        return getMessages.getMessage(messageId, user, id, character);
    }

    public Integer setReplyMessage(Integer messageId, String user, NewMessage newMessage, Integer id, Character character) {
        return setMessages.setReplyMG(messageId, user, newMessage, id, character);
    }

    public Boolean changePinned(UpdateMessage updateMessage, Integer messageId, String user) { return modifyMessages.setOrUnsetPinned(updateMessage, messageId, user); }


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


    public List<CategoriesMessages> getCategories(String user) {
        List<CategoriesMessages> categories = new ArrayList<>(entityManager.createQuery("SELECT p FROM Person p WHERE p.username =: username", Person.class)
                .setParameter("username", user).getSingleResult().getCategories());
        categories.sort(Comparator.comparing( e -> e.getCategory()));
        return categories;
    }

}




