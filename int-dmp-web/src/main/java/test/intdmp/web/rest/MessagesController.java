package test.intdmp.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.intdmp.core.model.person.messages.CategoriesMessages;
import test.intdmp.core.model.person.messages._enum.MessageType;
import test.intdmp.core.service.MessagesService;
import test.intdmp.core.service.messages._class.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    @Autowired
    private MessagesService messagesService;

    @RequestMapping(value = "/{projectId}/received", method = RequestMethod.POST)
    public NumberAndListDisplayMessages getReceivedMessages(@RequestBody PaginatorFilter paginator, @PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return messagesService.getReceivedMessages(projectId, user, paginator);
    }

    @RequestMapping(value = "/{projectId}/sent", method = RequestMethod.POST)
    public NumberAndListDisplayMessages getSentMessages(@RequestBody PaginatorFilter paginator, @PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return messagesService.getSentMessages(projectId, user, paginator);
    }

    @RequestMapping(value = "/{projectId}/pinned", method = RequestMethod.POST)
    public NumberAndListDisplayMessages getSentPinnedMessages(@RequestBody PaginatorFilter paginator, @PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return messagesService.getPinnedMessages(projectId, user, paginator);
    }

    @RequestMapping(value = "/{projectId}/users", method = RequestMethod.GET)
    public List<SuggestPerson> getSuggestUsers(@PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return  messagesService.getSuggestUsers(projectId, user);
    }

    @RequestMapping(value = "/{projectId}/newMessage", method = RequestMethod.POST)
    public Integer createMessage(@RequestBody NewMessage newMessage, @PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return  messagesService.setNewMessage(newMessage, projectId, user);
    }

    @RequestMapping(value = "/detailedMessage/{messageId}", method = RequestMethod.GET)
    public DetailedMessage detailedMessage(@PathVariable("messageId") Integer messageId, @RequestParam("user") String user, @RequestParam("id") Integer id, @RequestParam("char") MessageType messageType) {
        return  messagesService.getDetailedMessage(messageId, user, id, messageType);
    }

    @RequestMapping(value = "/detailedMessage/{messageId}", method = RequestMethod.POST)
    public Integer setReplyMessage(@RequestBody NewMessage newMessage, @PathVariable("messageId") Integer messageId, @RequestParam("user") String user, @RequestParam("id") Integer id, @RequestParam("char") MessageType character) {
        return  messagesService.setReplyMessage(messageId, user, newMessage, id, character);
    }

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public List<CategoriesMessages> getUserCategories(@RequestParam("user") String user) {
        return  messagesService.getUserCategories(user);
    }

    @RequestMapping(value = "/detailedMessage/{messageId}", method = RequestMethod.PATCH)
    public Boolean setOrUnsetPinned(@RequestBody UpdateMessage updateMessage, @PathVariable("messageId") Integer messageId, @RequestParam("user") String user) {
        return  messagesService.changePinned(updateMessage, messageId, user);
    }

}