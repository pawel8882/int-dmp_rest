package test.intdmp.core.helpClass;

import test.intdmp.core.model.person.messages.DataMessages;

import java.util.List;

public class SentMessages {

    public DataMessages dataMessages;
    public List<SuggestPerson> toPersons;

    public SentMessages(DataMessages dataMessages, List<SuggestPerson> toPersons) {
        this.dataMessages = dataMessages;
        this.toPersons= toPersons;

    }

}
