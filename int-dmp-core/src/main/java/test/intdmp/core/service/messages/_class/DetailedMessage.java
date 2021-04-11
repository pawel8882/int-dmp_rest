package test.intdmp.core.service.messages._class;
import test.intdmp.core.model.person.messages._enum.MessageType;

import test.intdmp.core.model.messages.*;

import java.util.*;

public class DetailedMessage {

    public String header;
    public Message message;
    public SuggestPerson owner;
    public List<SuggestPerson> toPersons;
    public List<SuggestPerson> dwPersons;
    public MessageType messageType;
    public Integer id;
    public Boolean pinned;

    public DetailedMessage(String header, Message message, SuggestPerson owner, List<SuggestPerson> toPersons, List<SuggestPerson> dwPersons, MessageType messageType, Integer id, Boolean pinned) {
        this.header = header;
        this.message = message;
        this.owner = owner;
        this.toPersons= toPersons;
        this.dwPersons= dwPersons;
        this.messageType = messageType;
        this.id = id;
        this.pinned = pinned;

    }

}
