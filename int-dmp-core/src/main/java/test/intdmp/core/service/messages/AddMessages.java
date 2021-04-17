package test.intdmp.core.service.messages;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import test.intdmp.core.model.messages.Header;
import test.intdmp.core.model.messages.Message;
import test.intdmp.core.model.messages.ReplyMessage;
import test.intdmp.core.model.person.messages.*;
import test.intdmp.core.model.person.messages._enum.MessageType;
import test.intdmp.core.model.projects.Person;
import test.intdmp.core.model.projects.Project;
import test.intdmp.core.service.messages._class.NewMessage;
import test.intdmp.core.service.messages._class.SuggestPerson;
import test.intdmp.core.service.repository.*;

import javax.persistence.EntityManager;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Component
public class AddMessages {

    @Autowired
    private EntityManager entityManager;
    @Autowired
    private ModifyMessages modifyMessages;
    @Autowired
    private ReceivedMessagesRepository receivedMessagesRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private DataMessagesRepository dataMessagesRepository;
    @Autowired
    private SentMessagesRepository sentMessagesRepository;
    @Autowired
    private InformationOnlyMessagesRepository informationOnlyMessagesRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private HeaderRepository headerRepository;
    @Autowired
    private InfoAboutMessagesRepository infoAboutMessagesRepository;
    @Autowired
    private ReplyMessagesRepository replyMessagesRepository;
    @Autowired
    private DataReplyMessagesRepository dataReplyMessagesRepository;


    private final String defaultCategory = "Ogólny";


    public Integer addMessage(NewMessage newMessage, Integer projectId, String user) {
        Date date = new Date();
        Person person = personRepository.findByUsername(user);
        Project project = projectRepository.getOneById(projectId);
        Header header = new Header();
        Message message = new Message();
        message.setContent(newMessage.content);
        message.setTimestamp(new Timestamp(date.getTime()));
        header.setTitle(newMessage.header);
        header.setMessage(message);
        headerRepository.save(header);

        DataMessages dataMessage = new DataMessages();
        SentMessages sentMessage = new SentMessages();
        dataMessage.setHeader(header);
        dataMessage.setProject(project);
        dataMessage.setPerson(person);
        dataMessagesRepository.save(dataMessage);

        InfoAboutMessages info = new InfoAboutMessages();
        info.setPinned(false);
        infoAboutMessagesRepository.save(info);

        sentMessage.setCategoriesMessages(modifyMessages.userCategoryReference(person.getId(), this.defaultCategory));
        sentMessage.setOpened(true);
        sentMessage.setPerson(person);
        sentMessage.setInfo(info);
        sentMessage.setDataMessages(dataMessage);
        sentMessage.setTimestamp(new Timestamp(date.getTime()));
        sentMessagesRepository.save(sentMessage);

        Integer dataMessageId = dataMessage.getId();

        addReceivedMessages(dataMessageId, newMessage.toPersons, date);
        addInformationOnlyMessages(dataMessageId, newMessage.dwPersons, date);

        return dataMessageId;

    }

    public void addReceivedMessages(Integer dataMessageId, List<SuggestPerson> toPersons, Date date) {

        DataMessages dataMessage = dataMessagesRepository.getOneById(dataMessageId);
        toPersons.forEach(e -> {
            InfoAboutMessages info = new InfoAboutMessages();
            info.setPinned(false);
            infoAboutMessagesRepository.save(info);
            ReceivedMessages data = new ReceivedMessages();
            data.setOpened(false);
            data.setPerson(personRepository.getOneById(e.id));
            data.setDataMessages(dataMessage);
            data.setTimestamp(new Timestamp(date.getTime()));
            data.setCategoriesMessages(modifyMessages.userCategoryReference(e.id, this.defaultCategory));
            data.setInfo(info);
            receivedMessagesRepository.save(data);
        });
    }

    public void addInformationOnlyMessages(Integer dataMessageId, List<SuggestPerson> dwPersons, Date date) {

        DataMessages dataMessage = dataMessagesRepository.getOneById(dataMessageId);
        dwPersons.forEach(e -> {
            InfoAboutMessages info = new InfoAboutMessages();
            info.setPinned(false);
            infoAboutMessagesRepository.save(info);
            InformationOnlyMessages data = new InformationOnlyMessages();
            data.setOpened(false);
            data.setPerson(personRepository.getOneById(e.id));
            data.setDataMessages(dataMessage);
            data.setTimestamp(new Timestamp(date.getTime()));
            data.setCategoriesMessages(modifyMessages.userCategoryReference(e.id, this.defaultCategory));
            data.setInfo(info);
            informationOnlyMessagesRepository.save(data);
        });
    }

    public Integer setReplyMessage(Integer messageId, String user, NewMessage newMessage, Integer id, MessageType character) {

        Date date = new Date();
        DataReplyMessages replyData = new DataReplyMessages();
        ReplyMessage reply = new ReplyMessage();

        Message message = dataMessagesRepository.getOneById(messageId).getHeader().getMessage();
        Person person = personRepository.findByUsername(user);

        reply.setContent(newMessage.content);
        reply.setTimestamp(new Timestamp(date.getTime()));
        reply.setMessage(message);

        replyMessagesRepository.save(reply);

        replyData.setReplyMessage(reply);
        replyData.setDataMessages(message.getHeader().getDataMessages());
        replyData.setPerson(person);

        dataReplyMessagesRepository.save(replyData);


        DataMessages dataMessage = dataMessagesRepository.getOneById(messageId);
        tryModifySentMessageOrElseCreateNew(person, dataMessage, id, date);


        if (person.getId() == dataMessage.getPerson().getId())
            updateSentAndReceivedMessagesOwnerReply(date, dataMessage, person);
        else {
            updateReceivedMessagesPersonInToPersons(date, dataMessage, person, newMessage);
        }

        return reply.getId();


    }

    private void tryModifySentMessageOrElseCreateNew(Person person, DataMessages dataMessage, Integer id, Date date) {

        Boolean isPresent = false;
        try { isPresent = dataMessage.getSentMessages().stream().filter(e -> e.getPerson().getId().equals(person.getId())).findFirst()
                .map(u -> { updateSentMessage(u, date); return u; })
                .isPresent(); }
        catch (Exception e) {
        }
        if (!isPresent)
            createSentMessage(date, dataMessage, person, id);
    }

    private void createSentMessage(Date date, DataMessages dataMessage, Person person, Integer id) {

        SentMessages sentMessage = new SentMessages();
        sentMessage.setTimestamp(new Timestamp(date.getTime()));
        sentMessage.setOpened(true);
        sentMessage.setDataMessages(dataMessage);
        sentMessage.setPerson(person);
        sentMessage.setInfo(receivedMessagesRepository.getOneById(id).getInfo());
        sentMessage.setCategoriesMessages(modifyMessages.userCategoryReference(person.getId(), defaultCategory));
        sentMessagesRepository.save(sentMessage);

    }

    private void updateSentMessage(SentMessages sentMessage, Date date) {
        sentMessage.setTimestamp(new Timestamp(date.getTime()));
        sentMessagesRepository.save(sentMessage);
    }

    private void updateSentAndReceivedMessagesOwnerReply(Date date, DataMessages dataMessage, Person person) {

        dataMessage.getInformationOnlyMessages().forEach(
                e -> {
                    e.setTimestamp(new Timestamp(date.getTime()));
                    e.setOpened(false);
                    informationOnlyMessagesRepository.save(e);
                }
        );
        dataMessage.getReceivedMessages().forEach(
                e -> {
                    if (person.getId() != e.getPerson().getId()) {
                        e.setTimestamp(new Timestamp(date.getTime()));
                        e.setOpened(false);
                        receivedMessagesRepository.save(e);
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
                        receivedMessagesRepository.save(u);
                    }
                }));
        if (!dataMessage.getReceivedMessages().stream()
                .filter(e -> e.getPerson()
                        .getId().equals(dataMessage.getPerson().getId())).findFirst().isPresent()) {

            ReceivedMessages receivedMessage = new ReceivedMessages();
            receivedMessage.setTimestamp(new Timestamp(date.getTime()));
            receivedMessage.setOpened(false);
            receivedMessage.setPerson(personRepository.getOneById(dataMessage.getPerson().getId()));
            receivedMessage.setDataMessages(dataMessage);
            receivedMessage.setCategoriesMessages(modifyMessages.userCategoryReference(dataMessage.getPerson().getId(), defaultCategory));
            dataMessage.getSentMessages().stream()
                    .filter(e -> e.getPerson().getId().equals(dataMessage.getPerson().getId())).findFirst().map(u -> { receivedMessage.setInfo(u.getInfo()); return u; });
            receivedMessagesRepository.save(receivedMessage);
        }

    }


}
