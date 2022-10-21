package ru.pfr.overpayments.model.ros.mapper;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.ros.dto.VozPereRosDto;
import ru.pfr.overpayments.model.ros.entity.VozPereRos;

@Component
public class VozPereRosMapper {

    public VozPereRosDto toDto(VozPereRos obj) {
        return VozPereRosDto.builder()
                .god(obj.getVozPereRosId().getGod())
                .mes(obj.getVozPereRosId().getMes())
                .id(obj.getVozPereRosId().getId())
                .doc(obj.getVozPereRosId().getDoc())
                .s(obj.getS())
                .build();
    }

    public VozPereRos fromDto(VozPereRosDto dto) {
        return VozPereRos.builder()
                /*.god(dto.getVozPereRosId().getGod())
                .mes(dto.getVozPereRosId().getMes())
                .id(dto.getVozPereRosId().getId())
                .doc(dto.getVozPereRosId().getDoc())
                .s(dto.getS())*/
                .build();
    }


}
