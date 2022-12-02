package ru.pfr.overpayments.model.overpayment.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class CarerDto extends CitizenDto{
    @Builder
    public CarerDto(Long id, String id_ros, String snils, String surname, String name, String patronymic, String pol, LocalDate rdat, String adrreg, String tel, LocalDate dsm, DistrictDto districtDto) {
        super(id, id_ros, snils, surname, name, patronymic, pol, rdat, adrreg, tel, dsm, districtDto);
    }
}
