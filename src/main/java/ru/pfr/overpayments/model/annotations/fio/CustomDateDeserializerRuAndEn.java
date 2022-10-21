package ru.pfr.overpayments.model.annotations.fio;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CustomDateDeserializerRuAndEn extends StdDeserializer<LocalDate> {

    private static final DateTimeFormatter formatterEn
            = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private static final DateTimeFormatter formatterRu
            = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public CustomDateDeserializerRuAndEn() {
        this(null);
    }

    public CustomDateDeserializerRuAndEn(Class<?> vc) {
        super(vc);
    }

    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String date = jsonParser.getText();
        try {
            return LocalDate.parse(date, formatterEn);
        } catch (DateTimeParseException e) {
            try {
                return LocalDate.parse(date, formatterRu);
            } catch (DateTimeParseException e2) {
                throw new RuntimeException("DateTime parse exception" + date);
            }
        }
    }

}
