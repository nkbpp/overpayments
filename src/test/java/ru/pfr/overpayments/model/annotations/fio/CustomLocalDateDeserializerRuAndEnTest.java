package ru.pfr.overpayments.model.annotations.fio;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Data
class TestDeserializerObject {
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    public LocalDate date;
}

class CustomLocalDateDeserializerRuAndEnTest {

    @Test
    public void whenDeserializingUsingJsonDeserializeRu_thenCorrect()
            throws IOException {
        String json
                = "{\"date\":\"13.08.1993\"}";
        LocalDate expectedDate = LocalDate.of(1993,8,13);

        TestDeserializerObject event = new ObjectMapper()
                .readerFor(TestDeserializerObject.class)
                .readValue(json);

        assertThat(event.date).isEqualTo(expectedDate);
    }

    @Test
    public void whenDeserializingUsingJsonDeserializeEn_thenCorrect()
            throws IOException {
        String json
                = "{\"date\":\"1993-08-13\"}";
        LocalDate expectedDate = LocalDate.of(1993,8,13);

        TestDeserializerObject event = new ObjectMapper()
                .readerFor(TestDeserializerObject.class)
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
                .readerFor(TestDeserializerObject.class)
                .readValue(json)
        );

        assertThat(thrown).hasMessageContaining("LocalDate parse exception");
    }

}