package ru.pfr.overpayments.model.overpayment.entity.referenceBook;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.SpecificationOfTheReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.SpecificationOfTheReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.service.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsService;

import java.io.IOException;
import java.time.format.DateTimeParseException;

public class CustomDeserializerSpecificationOfTheReasonsForOverpaymentsById extends StdDeserializer<SpecificationOfTheReasonsForOverpaymentsDto> {

    @Autowired
    private SpecificationOfTheReasonsForOverpaymentsService specificationOfTheReasonsForOverpaymentsService;

    @Autowired
    private SpecificationOfTheReasonsForOverpaymentsMapper specificationOfTheReasonsForOverpaymentsMapper;

    public CustomDeserializerSpecificationOfTheReasonsForOverpaymentsById() {
        this(null);
    }

    public CustomDeserializerSpecificationOfTheReasonsForOverpaymentsById(Class<?> vc) {
        super(vc);
    }

    @Override
    public SpecificationOfTheReasonsForOverpaymentsDto deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        try {
            var reasonsForOverpayments = specificationOfTheReasonsForOverpaymentsService.findById(jsonParser.getValueAsLong());
            return reasonsForOverpayments == null? null:
                    specificationOfTheReasonsForOverpaymentsMapper.toDto(reasonsForOverpayments);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

}
