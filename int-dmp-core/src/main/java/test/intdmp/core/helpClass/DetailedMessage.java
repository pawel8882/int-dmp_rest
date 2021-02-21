package test.intdmp.core.helpClass;

import test.intdmp.core.model.messages.*;

import java.util.*;

public class DetailedMessage {

    public String header;
    public Message message;
    public SuggestPerson owner;
    public List<SuggestPerson> toPersons;
    public List<SuggestPerson> dwPersons;

    public DetailedMessage(String header, Message message, SuggestPerson owner, List<SuggestPerson> toPersons, List<SuggestPerson> dwPersons) {
        this.header = header;
        this.message = message;
        this.owner = owner;
        this.toPersons= toPersons;
        this.dwPersons= dwPersons;

    }

}
