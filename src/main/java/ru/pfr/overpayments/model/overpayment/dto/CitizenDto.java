package ru.pfr.overpayments.model.overpayment.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateSerializerRu;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class CitizenDto {

    private Long id;

    private String id_ros;

    private String snils;

    //@NotNull(message = "surname cannot be null")
    private String surname;

    //@NotNull(message = "name cannot be null")
    private String name;

    //@NotNull(message = "patronymic cannot be null")
    private String patronymic;

    private String pol;

    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate rdat;

    private String adrreg;

    private String tel;

    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate dsm; //дата смерти

    private DistrictDto districtDto;
}
