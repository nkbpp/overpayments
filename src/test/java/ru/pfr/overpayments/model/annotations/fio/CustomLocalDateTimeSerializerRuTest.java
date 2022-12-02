package ru.pfr.overpayments.model.annotations.fio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;

@Data
@AllArgsConstructor
class TestLocalDateTimeSerializerObject {
    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    public LocalDateTime date;
}

class CustomLocalDateTimeSerializerRuTest {

    @Test
    public void whenSerializingUsingJsonSerialize_thenCorrect()
            throws JsonProcessingException, ParseException {

        LocalDateTime date = LocalDateTime.of(1993,8,13,0,0,0);

        TestLocalDateTimeSerializerObject event = new TestLocalDateTimeSerializerObject(date);
        String resultDate = new ObjectMapper().writeValueAsString(event);

        assertThat(resultDate).contains("13.08.1993");
    }

}