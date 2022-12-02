package ru.pfr.overpayments.model.annotations.fio;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Data
class TestDeserializerLocalDateTimeObject {
    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEn.class)
    public LocalDateTime date;
}

class CustomLocalDateTimeDeserializerRuAndEnTest {

    @Test
    public void whenDeserializingUsingJsonDeserializeRu_thenCorrect()
            throws IOException {
        String json
                = "{\"date\":\"13.08.1993\"}";
        LocalDateTime expectedDate = LocalDateTime.of(1993, 8, 13, 0, 0, 0);

        TestDeserializerLocalDateTimeObject event = new ObjectMapper()
                .readerFor(TestDeserializerLocalDateTimeObject.class)
                .readValue(json);

        assertThat(event.date).isEqualTo(expectedDate);
    }

    @Test
    public void whenDeserializingUsingJsonDeserializeEn_thenCorrect()
            throws IOException {
        String json
                = "{\"date\":\"1993-08-13\"}";
        LocalDateTime expectedDate = LocalDateTime.of(1993, 8, 13, 0, 0, 0);

        TestDeserializerLocalDateTimeObject event = new ObjectMapper()
                .readerFor(TestDeserializerLocalDateTimeObject.class)
                .readValue(json);

        assertThat(event.date).isEqualTo(expectedDate);
    }

/*    @Test
    public void whenDeserializingUsingJsonDeserializeRu_thenInCorrect()
            throws IOException {
        String json
                = "{\"date\":\"\"}";

        TestDeserializerObject event = new ObjectMapper()
                .readerFor(TestDeserializerObject.class)
                .readValue(json);

        assertThat(event.date).isNull();
    }*/

    @Test
    public void whenDeserializingUsingJsonDeserializeRu_thenInCorrect() {
        String json
                = "{\"date\":\"\"}";
        //LocalDate expectedDate = LocalDate.of(1993,8,13);

        JsonMappingException thrown = assertThrows(JsonMappingException.class,
                () -> new ObjectMapper()
                        .readerFor(TestDeserializerLocalDateTimeObject.class)
                        .readValue(json)
        );

        assertThat(thrown).hasMessageContaining("LocalDate parse exception");
    }

}