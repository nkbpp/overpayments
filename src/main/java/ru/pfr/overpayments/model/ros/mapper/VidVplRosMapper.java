package ru.pfr.overpayments.model.ros.mapper;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.ros.entity.VidVplRos;
import ru.pfr.overpayments.model.ros.dto.VidVplRosDto;

@Component
public class VidVplRosMapper {

    public VidVplRosDto toDto(VidVplRos obj) {
        return VidVplRosDto.builder()
                .kod(obj.getKod())
                .name(obj.getName())
                .build();
    }

    public VidVplRos fromDto(VidVplRosDto dto) {
        return VidVplRos.builder()
                .kod(dto.getKod())
                .name(dto.getName())
                .build();
    }


}
