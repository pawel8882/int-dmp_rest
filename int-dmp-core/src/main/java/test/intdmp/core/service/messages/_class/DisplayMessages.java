package test.intdmp.core.service.messages._class;

import test.intdmp.core.model.person.messages.CategoriesMessages;
import test.intdmp.core.model.person.messages._enum.MessageType;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class DisplayMessages {

    public Integer messageId;
    public List<SuggestPerson> toPersons;
    public String title;
    public Timestamp timestamp;
    public CategoriesMessages category;
    public Boolean opened;
    public MessageType messageType;
    public Integer id;
    public Boolean pinned;

        public DisplayMessages(Integer messageId, Set<SuggestPerson> toPersons, String title, Timestamp timestamp, CategoriesMessages category, Boolean opened, MessageType messageType, Integer id, Boolean pinned) {
            this.messageId = messageId;
            this.toPersons= new ArrayList<>(toPersons);
            this.title = title;
            this.timestamp = timestamp;
            this.category = category;
            this.opened = opened;
            this.messageType = messageType;
            this.id = id;
            this.pinned = pinned;

        }

    }
