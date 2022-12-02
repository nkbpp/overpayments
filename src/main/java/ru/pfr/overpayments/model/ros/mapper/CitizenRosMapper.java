package ru.pfr.overpayments.model.ros.mapper;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.ros.dto.CitizenRosDto;
import ru.pfr.overpayments.model.ros.entity.citizen.CitizenRos;

@Component
public class CitizenRosMapper {

    public CitizenRosDto toDto(CitizenRos obj) {
        return CitizenRosDto.builder()
                .id(obj.getId())
                .surname(obj.getSurname())
                .name(obj.getName())
                .patronymic(obj.getPatronymic())
                .rdat(obj.getRdat())
                .pol(obj.getPol())
                .adrfakt(obj.getAdrfakt())
                .adrreg(
                        (obj.getAdrreg()==null || obj.getAdrreg().equals(""))?
                        obj.getAdrfakt():obj.getAdrreg()
                )
/*                .fulladr(
                        (obj.getAdrfakt()==null || obj.getAdrfakt().equals(""))?
                                obj.getAdrreg():obj.getAdrfakt()
                )*/
                .tel(obj.getTel())
                .district(obj.getDistrict())
                .snils(obj.getSnils())
                .dsm(obj.getDsm())
                .build();
    }

    public CitizenRos fromDto(CitizenRosDto dto) {
        return CitizenRos.builder()
                .id(dto.getId())
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .rdat(dto.getRdat())
                .pol(dto.getPol())
                .adrfakt(dto.getAdrfakt())
                .adrreg(dto.getAdrreg())
                .tel(dto.getTel())
                .district(dto.getDistrict())
                .snils(dto.getSnils())
                .dsm(dto.getDsm())
                .build();
    }


}
