package test.intdmp.core.helpClass;

import test.intdmp.core.model.person.messages.CategoriesMessages;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UpdateMessage {

    public Integer messageId;
    public Character type;
    public Integer id;
    public Boolean pinned;

    public UpdateMessage(Integer messageId, Character type, Integer id, Boolean pinned) {
        this.messageId = messageId;
        this.type = type;
        this.id = id;
        this.pinned = pinned;

    }

}
