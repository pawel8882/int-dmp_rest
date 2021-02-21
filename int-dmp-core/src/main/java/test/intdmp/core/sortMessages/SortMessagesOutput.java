package test.intdmp.core.sortMessages;
import test.intdmp.core.helpClass.*;
import test.intdmp.core.model.messages.ReplyMessage;
import test.intdmp.core.model.person.messages.CategoriesMessages;
import test.intdmp.core.model.person.messages.DataMessages;
import test.intdmp.core.model.person.messages.InformationOnlyMessages;
import test.intdmp.core.model.person.messages.ReceivedMessages;
import test.intdmp.core.model.projects.PersonsProjects;
import test.intdmp.core.service.MessagesService;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class SortMessagesOutput  {


    public ParamDisplayMessages GetSortedReceived(PaginatorFilter paginator, Set<ReceivedMessages> receivedMessages, Set<InformationOnlyMessages> informationMessages, Integer projectId, Integer personId) {
        List<DisplayMessages> ToSorted = new ArrayList<>();
        receivedMessages.removeIf(e -> (e.getDataMessages().getProject().getId() != projectId));
        informationMessages.removeIf(e -> (e.getDataMessages().getProject().getId() != projectId));
        receivedMessages.forEach(e -> {
            if (e.getDataMessages().getHeader().getMessage().getReplyMessages().stream().filter(u -> u.getOwner().id != personId).findFirst().isPresent()) {
                ReplyMessage replyMessage = e.getDataMessages().getPersonReplyMessageTheNewest(personId);
                ToSorted.add(new DisplayMessages(e.getDataMessages().getId(), replyMessage.getOwnerLikeSet(), e.getDataMessages().getHeader().getTitle(), replyMessage.getTimestamp(), e.getCategory(), e.getOpened()));
            } else {
                ToSorted.add(new DisplayMessages(e.getDataMessages().getId(), e.getDataMessages().getPersonLikeSuggestPersonTheNewest(personId), e.getDataMessages().getHeader().getTitle(), e.getTimestamp(), e.getCategory(), e.getOpened()));

            }

        });
        informationMessages.forEach(e -> {
            ToSorted.add(new DisplayMessages(e.getDataMessages().getId(), e.getDataMessages().getPersonLikeSuggestPersonTheNewest(personId), e.getDataMessages().getHeader().getTitle(), e.getTimestamp(), e.getCategory(), e.getOpened()));
        });
        ToSorted.sort(Comparator.comparing((DisplayMessages e) -> e.timestamp).reversed());
        return PaginatorSort(paginator, ToSorted);
    }

    public ParamDisplayMessages GetSortedSent(PaginatorFilter paginator, Set<DataMessages> dataMessages, Set<ReceivedMessages> replyMessages, Integer projectId, Integer personId) {
        List<DisplayMessages> ToSorted = new ArrayList<>();
        dataMessages.removeIf(e -> (e.getProject().getId() != projectId));
        replyMessages.removeIf(e -> (e.getDataMessages().getProject().getId() != projectId));
        replyMessages.removeIf(e -> (e.getDataMessages().getPerson().getId() == e.getPerson().getId()));
        dataMessages.forEach(e -> {
            ToSorted.add(new DisplayMessages(e.getId(), e.getPersonsWithoutCurrentUser(personId), e.getHeader().getTitle(), e.TheNewestTimestampForOwnerThisMessage(personId), e.getCategory(), e.getOpened()));
        });
        replyMessages.forEach(e -> {
            if (e.getDataMessages().getHeader().getMessage().getReplyMessages().stream().filter(u -> u.getOwner().id == personId).findFirst().isPresent()) {
                ReplyMessage replyMessage = e.getDataMessages().getPersonLikeSuggestPersonTheNewestSent(personId);
                ToSorted.add(new DisplayMessages(e.getDataMessages().getId(), e.getDataMessages().getPersonsWithoutCurrentUser(personId), e.getDataMessages().getHeader().getTitle(), replyMessage.getTimestamp(), e.getCategory(), replyMessage.getDataReplyMessage().getUpdate()));
            }
        });
        ToSorted.sort(Comparator.comparing((DisplayMessages e) -> e.timestamp).reversed());
        return PaginatorSort(paginator, ToSorted);
    }

    public ParamDisplayMessages PaginatorSort(PaginatorFilter paginator, List<DisplayMessages> messages) {

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
        ParamDisplayMessages paramDisplayMessages = new ParamDisplayMessages(sortMessages.subList(MinRange, MaxRange), sortMessages.size());
        return paramDisplayMessages;
    }
}
