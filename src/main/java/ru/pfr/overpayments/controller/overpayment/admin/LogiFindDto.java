package ru.pfr.overpayments.controller.overpayment.admin;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateTimeDeserializerRuAndEn;
import ru.pfr.overpayments.model.overpayment.dto.log.TypeLogDeserializer;
import ru.pfr.overpayments.model.overpayment.entity.log.TypeLog;

import java.time.LocalDateTime;

@Data
public class LogiFindDto {

    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEn.class)
    private LocalDateTime dateS;

    @JsonDeserialize(using = CustomLocalDateTimeDeserializerRuAndEn.class)
    private LocalDateTime datePo;

    private String user;

    @JsonDeserialize(using = TypeLogDeserializer.class)
    private TypeLog type;

}
