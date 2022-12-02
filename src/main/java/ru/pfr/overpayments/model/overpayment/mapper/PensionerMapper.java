package ru.pfr.overpayments.model.overpayment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.PensionerDto;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;

@RequiredArgsConstructor
@Component
public class PensionerMapper {

    //DistrictService districtService;

    public PensionerDto toDto(Pensioner obj) {
        return PensionerDto.builder()
                .id(obj.getId())
                .id_ros(obj.getIdRos())
                .snils(obj.getSnils())
                .surname(obj.getSurname())
                .name(obj.getName())
                .patronymic(obj.getPatronymic())
                .pol(obj.getPol())
                .adrreg(obj.getAdrreg())
                .rdat(obj.getRdat())
                .tel(obj.getTel())
                .dsm(obj.getDsm())
                .build();
    }

/*    public Pensioner fromDto(PensionerDto dto) {
        return Pensioner.builder()
                .id_ros(dto.getId_ros())
                .snils(dto.getSnils())
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .adrreg(dto.getAdrreg())
                .tel(dto.getTel())
                .district(districtService.findByKod(citizenRos.getDistrict()))
                .build();
    }*/

/*    public PensionerOverpayment fromDto(AddPensionerDto dto) {

        return PensionerOverpayment.builder()
                .id_ros(dto.getId_ros())
                .snils(dto.getSnils())
                .adrreg(dto.getAdrreg())
                .tel(dto.getTel())

*//*                .snils(dto.getSnils())
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .adrreg(dto.getAdrreg())
                .tel(dto.getTel())
                .carers(carer)
                .dependents(dependent)
                .district(districtService.findByKod(citizenRos.getDistrict()))*//*
                .build();
    }*/

}
