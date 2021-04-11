package test.intdmp.core.service.messages._class;

import test.intdmp.core.model.person.messages._enum.MessageType;

public class UpdateMessage {

    public Integer messageId;
    public MessageType messageType;
    public Integer id;
    public Boolean pinned;

    public UpdateMessage(Integer messageId, MessageType messageType, Integer id, Boolean pinned) {
        this.messageId = messageId;
        this.messageType = messageType;
        this.id = id;
        this.pinned = pinned;

    }

}
