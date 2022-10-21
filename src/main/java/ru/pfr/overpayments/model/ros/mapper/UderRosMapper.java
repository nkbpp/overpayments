package ru.pfr.overpayments.model.ros.mapper;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.ros.dto.UderRosDto;
import ru.pfr.overpayments.model.ros.entity.UderRos;

@Component
public class UderRosMapper {

    public UderRosDto toDto(UderRos obj) {
        return UderRosDto.builder()
                .recId(obj.getRecId())
                .god(obj.getGod())
                .mes(obj.getMes())
                .ub(obj.getUb())
                .us(obj.getUs())
                .uderPercent(obj.getUderPercent())
                .uSddpm(obj.getUSddpm())
                .ouSddpm(obj.getOuSddpm())
                .build();
    }

    public UderRos fromDto(UderRosDto dto) {
        return UderRos.builder()
                .recId(dto.getRecId())
                .god(dto.getGod())
                .mes(dto.getMes())
                .uderPercent(dto.getUderPercent())
                .ub(dto.getUb())
                .us(dto.getUs())
                .uSddpm(dto.getUSddpm())
                .ouSddpm(dto.getOuSddpm())
                .build();
    }


}
