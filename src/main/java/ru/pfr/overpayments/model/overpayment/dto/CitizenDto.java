package ru.pfr.overpayments.model.overpayment.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CitizenDto {

    private Long id;

    private String id_ros;

    private String snils;

    //@NotNull(message = "surname cannot be null")
    private String surname;

    //@NotNull(message = "name cannot be null")
    private String name;

    //@NotNull(message = "patronymic cannot be null")
    private String patronymic;

    private LocalDate rdat;

    private String adrreg;

    private String tel;

    private LocalDate dsm; //дата смерти

    private DistrictDto districtDto;
}
