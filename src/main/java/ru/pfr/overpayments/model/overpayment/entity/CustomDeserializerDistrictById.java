package ru.pfr.overpayments.model.overpayment.entity;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pfr.overpayments.service.overpayment.DistrictService;

import java.io.IOException;
import java.time.format.DateTimeParseException;

public class CustomDeserializerDistrictById extends StdDeserializer<District> {

    @Autowired
    private DistrictService districtService;

    public CustomDeserializerDistrictById() {
        this(null);
    }

    public CustomDeserializerDistrictById(Class<?> vc) {
        super(vc);
    }

    @Override
    public District deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            if (jsonParser.getText().equals("")) {
                return null;
            }
            return districtService.findByKod(jsonParser.getValueAsInt());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
