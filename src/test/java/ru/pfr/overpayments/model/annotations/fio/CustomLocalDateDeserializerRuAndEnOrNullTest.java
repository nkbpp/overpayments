package ru.pfr.overpayments.model.annotations.fio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

@Data
class TestDeserializerLocalDateOrNullObject {
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    public LocalDate date;
}

class CustomLocalDateDeserializerRuAndEnOrNullTest {

    @Test
    public void whenDeserializingUsingJsonDeserializeRu_thenCorrect()
            throws IOException {
        String json
                = "{\"date\":\"13.08.1993\"}";
        LocalDate expectedDate = LocalDate.of(1993, 8, 13);

        TestDeserializerLocalDateOrNullObject event = new ObjectMapper()
                .readerFor(TestDeserializerLocalDateOrNullObject.class)
                .readValue(json);

        assertThat(event.date).isEqualTo(expectedDate);
    }

    @Test
    public void whenDeserializingUsingJsonDeserializeEn_thenCorrect()
            throws IOException {
        String json
                = "{\"date\":\"1993-08-13\"}";
        LocalDate expectedDate = LocalDate.of(1993, 8, 13);

        TestDeserializerLocalDateOrNullObject event = new ObjectMapper()
                .readerFor(TestDeserializerLocalDateOrNullObject.class)
                .readValue(json);

        assertThat(event.date).isEqualTo(expectedDate);
    }

    @Test
    public void whenDeserializingUsingJsonDeserializeRu_thenInCorrect()
            throws IOException {
        String json
                = "{\"date\":\"\"}";

        TestDeserializerLocalDateOrNullObject event = new ObjectMapper()
                .readerFor(TestDeserializerLocalDateOrNullObject.class)
                .readValue(json);

        assertThat(event.date).isNull();
    }

}