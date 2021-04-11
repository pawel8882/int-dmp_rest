package test.intdmp.core.service.messages;

import org.springframework.stereotype.Component;
import test.intdmp.core.model.messages.ReplyMessage;
import test.intdmp.core.model.person.messages.InformationOnlyMessages;
import test.intdmp.core.model.person.messages.ReceivedMessages;
import test.intdmp.core.model.person.messages.SentMessages;
import test.intdmp.core.service.messages._class.DisplayMessages;
import test.intdmp.core.service.messages._class.NumberAndListDisplayMessages;
import test.intdmp.core.service.messages._class.PaginatorFilter;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Component
public class SortMessages {

    public NumberAndListDisplayMessages sortPinnedMessages(PaginatorFilter paginator, Set<SentMessages> sentMessages, Set<ReceivedMessages> receivedMessages, Set<InformationOnlyMessages> informationMessages, Integer projectId, Integer personId) {
        List<DisplayMessages> ToSortedReceived = new ArrayList<>(sortReceivedAndInformationOnlyByProjectAndTimestamp(receivedMessages, informationMessages, projectId, personId));
        List<DisplayMessages> ToSortedSent = new ArrayList<>(sortSentByProjectAndTimestamp(sentMessages, projectId, personId));
        List<DisplayMessages> ToSorted = new ArrayList<>();
        ToSorted.addAll(ToSortedReceived);
        ToSorted.addAll(ToSortedSent);
        ToSorted.sort(Comparator.comparing((DisplayMessages e) -> e.timestamp).reversed());
        return sortByPaginator(paginator, ToSorted);
    }

    public NumberAndListDisplayMessages sortReceivedMessages(PaginatorFilter paginator, Set<ReceivedMessages> receivedMessages, Set<InformationOnlyMessages> informationMessages, Integer projectId, Integer personId) {
        List<DisplayMessages> ToSorted = new ArrayList<>(sortReceivedAndInformationOnlyByProjectAndTimestamp(receivedMessages, informationMessages, projectId, personId));
        return sortByPaginator(paginator, ToSorted);
    }

    public NumberAndListDisplayMessages sortSentMessages(PaginatorFilter paginator, Set<SentMessages> sentMessages, Integer projectId, Integer personId) {
        List<DisplayMessages> ToSorted = new ArrayList<>(sortSentByProjectAndTimestamp(sentMessages, projectId, personId));
        return sortByPaginator(paginator, ToSorted);
    }


    public NumberAndListDisplayMessages sortByPaginator(PaginatorFilter paginator, List<DisplayMessages> messages) {

        List<DisplayMessages> sortMessages = new ArrayList<>(messages);

        if (!(paginator.search.replace(" ", "") == "")) {
            for (DisplayMessages e : messages) {
                if ((!e.title.toLowerCase().contains(paginator.search.toLowerCase())))
                    sortMessages.remove(e);
            }
        }

        Integer MaxRange = paginator.pageSize * (paginator.pageIndex+1);
        Integer MinRange = MaxRange - paginator.pageSize;

        if (sortMessages.size() < MaxRange) {
            MaxRange = sortMessages.size();
        }
        if (sortMessages.size() < MinRange) {
            MinRange = 0;

        }
        NumberAndListDisplayMessages numberAndListDisplayMessages = new NumberAndListDisplayMessages(sortMessages.subList(MinRange, MaxRange), sortMessages.size());
        return numberAndListDisplayMessages;
    }

    public List<DisplayMessages> sortReceivedAndInformationOnlyByProjectAndTimestamp(Set<ReceivedMessages> receivedMessages, Set<InformationOnlyMessages> informationMessages, Integer projectId, Integer personId) {
        List<DisplayMessages> ToSorted = new ArrayList<>();
        receivedMessages.removeIf(e -> (e.getDataMessages().getProject().getId() != projectId));
        informationMessages.removeIf(e -> (e.getDataMessages().getProject().getId() != projectId));
        receivedMessages.forEach(e -> {
            if (e.getDataMessages().getHeader().getMessage().getReplyMessages().stream().filter(u -> u.getOwner().id != personId).findFirst().isPresent()) {
                ReplyMessage replyMessage = e.getDataMessages().getPersonReplyMessageTheNewest(personId);
                ToSorted.add(new DisplayMessages(e.getDataMessages().getId(), replyMessage.getOwnerLikeSet(), e.getDataMessages().getHeader().getTitle(), replyMessage.getTimestamp(), e.getCategory(), e.getOpened(), e.getType(), e.getId(), e.getInfo().getPinned()));
            } else {
                ToSorted.add(new DisplayMessages(e.getDataMessages().getId(), e.getDataMessages().getPersonLikeSuggestPersonTheNewest(personId), e.getDataMessages().getHeader().getTitle(), e.getTimestamp(), e.getCategory(), e.getOpened(), e.getType(), e.getId(), e.getInfo().getPinned()));

            }

        });
        informationMessages.forEach(e -> {
            ToSorted.add(new DisplayMessages(e.getDataMessages().getId(), e.getDataMessages().getPersonLikeSuggestPersonTheNewest(personId), e.getDataMessages().getHeader().getTitle(), e.getTimestamp(), e.getCategory(), e.getOpened(), e.getType(), e.getId(), e.getInfo().getPinned()));
        });
        ToSorted.sort(Comparator.comparing((DisplayMessages e) -> e.timestamp).reversed());
        return ToSorted;
    }

    public List<DisplayMessages> sortSentByProjectAndTimestamp(Set<SentMessages> sentMessages, Integer projectId, Integer personId) {
        List<DisplayMessages> ToSorted = new ArrayList<>();
        sentMessages.removeIf(e -> (e.getDataMessages().getProject().getId() != projectId));
        sentMessages.forEach(e -> {
            ToSorted.add(new DisplayMessages(e.getDataMessages().getId(), e.getDataMessages().getPersonsWithoutCurrentUser(personId), e.getDataMessages().getHeader().getTitle(), e.getTimestamp(), e.getCategory(), e.getOpened(), e.getType(), e.getId(), e.getInfo().getPinned()));
        });
        ToSorted.sort(Comparator.comparing((DisplayMessages e) -> e.timestamp).reversed());
        return ToSorted;
    }

}
