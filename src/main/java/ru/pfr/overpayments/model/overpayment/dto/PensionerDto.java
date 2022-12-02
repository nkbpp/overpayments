package ru.pfr.overpayments.model.overpayment.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;


@Data
public class PensionerDto extends CitizenDto {

    @Builder
    public PensionerDto(Long id, String id_ros, String snils, String surname, String name, String patronymic, String pol, LocalDate rdat, String adrreg, String tel, LocalDate dsm, DistrictDto districtDto) {
        super(id, id_ros, snils, surname, name, patronymic, pol, rdat, adrreg, tel, dsm, districtDto);
    }
}
