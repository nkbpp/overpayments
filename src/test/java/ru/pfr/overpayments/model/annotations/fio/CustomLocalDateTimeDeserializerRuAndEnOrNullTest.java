package ru.pfr.overpayments.model.annotations.fio;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@Data
class TestDeserializerLocalDateTimeOrNullObject {
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEnOrNull.class)
    public LocalDateTime date;
}

class CustomLocalDateTimeDeserializerRuAndEnOrNullTest {

    @Test
    public void whenDeserializingUsingJsonDeserializeRu_thenCorrect()
            throws IOException {
        String json
                = "{\"date\":\"13.08.1993\"}";
        LocalDateTime expectedDate = LocalDateTime.of(1993, 8, 13, 0, 0, 0);

        TestDeserializerLocalDateTimeOrNullObject event = new ObjectMapper()
                .readerFor(TestDeserializerLocalDateTimeOrNullObject.class)
                .readValue(json);

        assertThat(event.date).isEqualTo(expectedDate);
    }

    @Test
    public void whenDeserializingUsingJsonDeserializeEn_thenCorrect()
            throws IOException {
        String json
                = "{\"date\":\"1993-08-13\"}";
        LocalDateTime expectedDate = LocalDateTime.of(1993, 8, 13, 0, 0, 0);

        TestDeserializerLocalDateTimeOrNullObject event = new ObjectMapper()
                .readerFor(TestDeserializerLocalDateTimeOrNullObject.class)
                .readValue(json);

        assertThat(event.date).isEqualTo(expectedDate);
    }

    @Test
    public void whenDeserializingUsingJsonDeserializeRu_thenInCorrect()
            throws IOException {
        String json
                = "{\"date\":\"\"}";

        TestDeserializerLocalDateTimeOrNullObject event = new ObjectMapper()
                .readerFor(TestDeserializerLocalDateTimeOrNullObject.class)
                .readValue(json);

        assertThat(event.date).isNull();
    }

}