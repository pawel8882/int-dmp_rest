package test.intdmp.core.configuration._class;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import test.intdmp.core.model.person.messages._enum.MessageType;

@Component
public class StringToEnumConvert implements Converter<String, MessageType> {

    @Override
    public MessageType convert(String s) {
        try {
            return MessageType.valueOf(s.toUpperCase());
        }
        catch (IllegalArgumentException e) {
            return null;
        }
    }
}
