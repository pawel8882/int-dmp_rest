package test.intdmp.core.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.intdmp.core.model.person.messages.*;
import test.intdmp.core.model.person.messages._enum.MessageType;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.service.messages.CollectMessages;
import test.intdmp.core.service.messages.ModifyMessages;
import test.intdmp.core.service.messages.AddMessages;
import test.intdmp.core.service.messages._class.*;
import test.intdmp.core.service.repository.PersonRepository;
import test.intdmp.core.service.repository.ProjectRepository;

import javax.persistence.EntityManager;
import java.util.*;

@Service
@Transactional
public class MessagesService{

    @Autowired
    private AddMessages addMessages;
    @Autowired
    private CollectMessages collectMessages;
    @Autowired
    private ModifyMessages modifyMessages;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private ProjectRepository projectRepository;

    public NumberAndListDisplayMessages getReceivedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return collectMessages.userReceivedAndInformationOnlyMessages(projectId, user, paginator);
    }

    public NumberAndListDisplayMessages getSentMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return collectMessages.userSentMessages(projectId, user, paginator);
    }

    public NumberAndListDisplayMessages getPinnedMessages(Integer projectId, String user, PaginatorFilter paginator) {
        return collectMessages.userPinnedMessages(projectId, user, paginator);
    }

    public List<SuggestPerson> getSuggestUsers(Integer projectId, String user) {
        return getSuggestions(projectId, user);
    }

    public List<CategoriesMessages> getUserCategories(String user) {
        return getCategories(user);
    }

    public Integer setNewMessage(NewMessage newMessage, Integer projectId, String user) {
        return addMessages.addMessage(newMessage, projectId, user);
    }

    public DetailedMessage getDetailedMessage(Integer messageId, String user, Integer id, MessageType messageType) {
        return collectMessages.detailedMessage(messageId, user, id, messageType);
    }

    public Integer setReplyMessage(Integer messageId, String user, NewMessage newMessage, Integer id, MessageType character) {
        return addMessages.setReplyMessage(messageId, user, newMessage, id, character);
    }

    public Boolean changePinned(UpdateMessage updateMessage, Integer messageId, String user) { return modifyMessages.setOrUnsetPinned(updateMessage, messageId, user); }


    public List<SuggestPerson> getSuggestions(Integer projectId, String user) {
        user.replace(" ", "");
        Set<PersonsProjects> PersonsProjects = projectRepository.findById(projectId).get().getPersons();
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
        List<CategoriesMessages> categories = new ArrayList<>(personRepository.findByUsername(user).getCategories());
        categories.sort(Comparator.comparing( e -> e.getCategory()));
        return categories;
    }

}




