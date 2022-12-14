package ru.pfr.overpayments.model.annotations.fio;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Data
@AllArgsConstructor
class TestLocalDateSerializerObject {
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    public LocalDate date;
}

class CustomLocalDateSerializerRuTest {

    @Test
    public void whenSerializingUsingJsonSerialize_thenCorrect()
            throws JsonProcessingException, ParseException {
        LocalDate date = LocalDate.of(1993,8,13);
        TestLocalDateSerializerObject event = new TestLocalDateSerializerObject(date);

        String resultDate = new ObjectMapper().writeValueAsString(event);

        assertThat(resultDate).contains("13.08.1993");
    }


/*    @Test
    public void whenSerializingInCorrect() throws JsonProcessingException {
        LocalDate date = null;
        TestLocalDateSerializerObject event = new TestLocalDateSerializerObject(null);

        String resultDate = new ObjectMapper().writeValueAsString(event);

        assertThat(resultDate).contains("13.08.1993");
    }*/

}