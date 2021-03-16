package test.intdmp.core.service.messages._class;

import java.util.*;

public class NewMessage {

    public String content;
    public String header;
    public List<SuggestPerson> toPersons;
    public List<SuggestPerson> dwPersons;

    public NewMessage(String content, String header, List<SuggestPerson> toPersons, List<SuggestPerson> dwPersons) {
        this.content = content;
        this.header = header;
        this.toPersons= toPersons;
        this.dwPersons= dwPersons;

    }

}
