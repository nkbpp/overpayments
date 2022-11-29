package ru.pfr.overpayments.model.overpayment.dto.log;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateTimeSerializerRu;
import ru.pfr.overpayments.model.overpayment.entity.log.TypeLog;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LogiDto {

    private Long id;

    @JsonSerialize(using = CustomLocalDateTimeSerializerRu.class)
    private LocalDateTime date;

    private String user;

    @JsonDeserialize(using = TypeLogDeserializer.class)
    private TypeLog type;

    private String text;

}
