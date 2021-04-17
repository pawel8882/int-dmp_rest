package test.intdmp.core.service.messages.displayMessageCreator;

import org.springframework.stereotype.Service;
import test.intdmp.core.model.messages.ReplyMessage;
import test.intdmp.core.model.person.messages.InformationOnlyMessages;
import test.intdmp.core.model.person.messages.ReceivedMessages;
import test.intdmp.core.model.person.messages.SentMessages;
import test.intdmp.core.service.messages._class.DisplayMessages;

@Service
public class DisplayMessageCreator {

    public DisplayMessages CreateReceivedNewestReplyMessage(ReceivedMessages received, ReplyMessage reply ) {
        return new DisplayMessages(received.getDataMessages().getId(),
                reply.getOwnerLikeSet(),
                received.getDataMessages().getHeader().getTitle(),
                reply.getTimestamp(),
                received.getCategory(),
                received.getOpened(),
                received.getType(),
                received.getId(),
                received.getInfo().getPinned());
    }

    public DisplayMessages CreateReceivedOwnerIsPresent(ReceivedMessages received) {
        return new DisplayMessages(received.getDataMessages().getId(),
                received.getDataMessages().getOwnerLikeSet(),
                received.getDataMessages().getHeader().getTitle(),
                received.getTimestamp(),
                received.getCategory(),
                received.getOpened(),
                received.getType(),
                received.getId(),
                received.getInfo().getPinned());
    }

    public DisplayMessages CreateInformationOnly(InformationOnlyMessages infoOnly) {
        return new DisplayMessages(infoOnly.getDataMessages().getId(),
                infoOnly.getDataMessages().getOwnerLikeSet(),
                infoOnly.getDataMessages().getHeader().getTitle(),
                infoOnly.getTimestamp(),
                infoOnly.getCategory(),
                infoOnly.getOpened(),
                infoOnly.getType(),
                infoOnly.getId(),
                infoOnly.getInfo().getPinned());
    }

    public DisplayMessages CreateSent(SentMessages sent) {
        return new DisplayMessages(sent.getDataMessages().getId(),
                sent.getDataMessages().getOwnerLikeSet(),
                sent.getDataMessages().getHeader().getTitle(),
                sent.getTimestamp(),
                sent.getCategory(),
                sent.getOpened(),
                sent.getType(),
                sent.getId(),
                sent.getInfo().getPinned());
    }

}
