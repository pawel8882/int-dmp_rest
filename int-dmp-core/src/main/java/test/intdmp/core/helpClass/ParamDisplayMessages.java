package test.intdmp.core.helpClass;

import test.intdmp.core.model.person.messages.CategoriesMessages;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ParamDisplayMessages {

    public List<DisplayMessages> messages;
    public Integer messagesNumber;

    public ParamDisplayMessages(List<DisplayMessages> messages, Integer messagesNumber) {
        this.messages = messages;
        this.messagesNumber = messagesNumber;

    }

}
