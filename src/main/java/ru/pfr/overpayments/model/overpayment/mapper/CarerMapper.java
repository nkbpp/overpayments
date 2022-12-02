package ru.pfr.overpayments.model.overpayment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.CarerDto;
import ru.pfr.overpayments.model.overpayment.entity.Carer;
import ru.pfr.overpayments.model.ros.entity.citizen.CitizenRos;
import ru.pfr.overpayments.service.overpayment.DistrictService;

@Component
@RequiredArgsConstructor
public class CarerMapper {

    private final DistrictService districtService;

    private final DistrictMapper districtMapper;

    public CarerDto toDto(Carer obj) {
        return CarerDto.builder()
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
                .districtDto(districtMapper.toDto(obj.getDistrict()))
                .dsm(obj.getDsm())
                .build();
    }


    public Carer fromDto(CitizenRos dto) {
        return Carer.builder()
                .idRos(dto.getId())
                .snils(dto.getSnils())
                .surname(dto.getSurname())
                .name(dto.getName())
                .patronymic(dto.getPatronymic())
                .pol(dto.getPol())
                .adrreg(
                        (dto.getAdrreg()==null || dto.getAdrreg().equals(""))?
                                dto.getAdrfakt():dto.getAdrreg()
                )
                .rdat(dto.getRdat())
                .tel(dto.getTel())
                .district(districtService.findByKod(dto.getDistrict()))
                .dsm(dto.getDsm())
                .build();
    }

}
