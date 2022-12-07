package ru.pfr.overpayments.model.overpayment.entity.referenceBook;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;

import java.io.IOException;
import java.time.format.DateTimeParseException;

public class CustomDeserializerReasonsForOverpaymentsById extends StdDeserializer<ReasonsForOverpaymentsDto> {

    @Autowired
    private ReasonsForOverpaymentsService reasonsForOverpaymentsService;

    @Autowired
    private ReasonsForOverpaymentsMapper reasonsForOverpaymentsMapper;

    public CustomDeserializerReasonsForOverpaymentsById() {
        this(null);
    }

    public CustomDeserializerReasonsForOverpaymentsById(Class<?> vc) {
        super(vc);
    }

    @Override
    public ReasonsForOverpaymentsDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            var reasonsForOverpayments = reasonsForOverpaymentsService.findById(jsonParser.getValueAsLong());
            return reasonsForOverpayments == null? null:
                    reasonsForOverpaymentsMapper.toDto(reasonsForOverpayments);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
