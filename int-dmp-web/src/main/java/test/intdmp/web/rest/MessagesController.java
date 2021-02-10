package test.intdmp.web.rest;

import org.springframework.web.bind.annotation.*;
import test.intdmp.core.helpClass.NewMessage;
import test.intdmp.core.helpClass.SuggestPerson;
import test.intdmp.core.model.person.messages.DataMessages;
import test.intdmp.core.model.person.messages.ReceivedMessages;
import test.intdmp.core.service.MessagesService;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @RequestMapping(value = "/{projectId}/received", method = RequestMethod.GET)
    public List<ReceivedMessages> getHeaders(@PathVariable("projectId") Integer projectId, @RequestParam("user") String user, @RequestParam("min") Integer MinRange, @RequestParam("max") Integer MaxRange) {
        return messagesService.getHeaders(projectId, user, MinRange, MaxRange);
    }

    @RequestMapping(value = "/{projectId}/sent", method = RequestMethod.GET)
    public List<DataMessages> getSentHeaders(@PathVariable("projectId") Integer projectId, @RequestParam("user") String user, @RequestParam("min") Integer MinRange, @RequestParam("max") Integer MaxRange) {
        return messagesService.getSentHeaders(projectId, user, MinRange, MaxRange);
    }

    @RequestMapping(value = "/{projectId}/users", method = RequestMethod.GET)
    public List<SuggestPerson> getSuggestUsers(@PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return  messagesService.getSuggestUsers(projectId, user);
    }

    @RequestMapping(value = "/{projectId}/newMessage", method = RequestMethod.POST)
    public Integer createMessage(@RequestBody NewMessage newMessage, @PathVariable("projectId") Integer projectId, @RequestParam("user") String user) {
        return  messagesService.setNewMessage(newMessage, projectId, user);
    }
}
