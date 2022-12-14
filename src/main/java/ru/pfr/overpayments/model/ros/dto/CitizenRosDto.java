package ru.pfr.overpayments.model.ros.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.annotations.district.District;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateDeserializerRuAndEn;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenRosDto {

    @NotNull(message = "id cannot be null")
    private String id;

    @NotNull(message = "surname cannot be null")
    private String surname;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "patronymic cannot be null")
    private String patronymic;

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    private LocalDate rdat;

    private String pol;

    private String adrfakt;

    private String adrreg;

    private String fulladr;

    private String tel;

    @District
    private Integer district;

    private String snils;

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEn.class)
    private LocalDate dsm; //дата смерти

}
