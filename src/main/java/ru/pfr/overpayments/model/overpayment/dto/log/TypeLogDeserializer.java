package ru.pfr.overpayments.model.overpayment.dto.log;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import ru.pfr.overpayments.model.overpayment.entity.log.TypeLog;

import java.io.IOException;

public class TypeLogDeserializer extends StdDeserializer<TypeLog> {

    public TypeLogDeserializer() {
        this(null);
    }

    public TypeLogDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public TypeLog deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            return TypeLog.valueOf(jsonParser.getText());
        } catch (Exception e) {
            return null;
        }
    }

}
