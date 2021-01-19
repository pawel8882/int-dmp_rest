package test.intdmp.web.rest;

import org.springframework.web.bind.annotation.*;
import test.intdmp.core.model.messages.*;
import test.intdmp.core.model.department;
import test.intdmp.core.service.MessagesService;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/messages")
public class MessagesController {

    private MessagesService messagesService;

    MessagesController(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Header> getHeaders() {
        return messagesService.getHeaders();
    }

}
