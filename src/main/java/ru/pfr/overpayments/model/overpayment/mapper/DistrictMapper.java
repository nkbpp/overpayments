package ru.pfr.overpayments.model.overpayment.mapper;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.DistrictDto;
import ru.pfr.overpayments.model.overpayment.entity.District;

@Component
public class DistrictMapper {

    public DistrictDto toDto(District obj) {
        return DistrictDto.builder()
                .id(obj.getId())
                .kod(obj.getKod())
                .name(obj.getName())
                .build();
    }


    public District fromDto(DistrictDto dto) {
        return District.builder()
                .id(dto.getId())
                .kod(dto.getKod())
                .name(dto.getName())
                .build();
    }


}
