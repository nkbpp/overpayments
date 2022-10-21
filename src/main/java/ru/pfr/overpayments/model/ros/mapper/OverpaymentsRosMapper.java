package ru.pfr.overpayments.model.ros.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.ros.dto.OverpaymentRosDto;
import ru.pfr.overpayments.model.ros.entity.OverpaymentRos;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class OverpaymentsRosMapper {

    private final VidVplRosMapper vidVplRosMapper;

    private final UderRosMapper uderRosMapper;
    private final VozPereRosMapper vozPereRosMapper;

    public OverpaymentRosDto toDto(OverpaymentRos obj) {
        return OverpaymentRosDto.builder()
                .id(obj.getId())
                .isId(obj.getIs_id())
                .doc(obj.getDoc())
                .close_date(obj.getClose_date())
                .spe(obj.getSpe())
                .sroks(obj.getSroks())
                .srokpo(obj.getSrokpo())
                .vinap(obj.getVinap())
                .docdv(obj.getDocdv())
                .vidVpl(vidVplRosMapper.toDto(obj.getVidVpl()))
                .uderRosDto(obj.getUderRos().stream()
                        .map(uderRos -> uderRosMapper.toDto(uderRos))
                                .collect(Collectors.toList())
                        )
                .vozPereRosDto(obj.getVozPereRos().stream()
                        .map(vozPereRos -> vozPereRosMapper.toDto(vozPereRos))
                        .collect(Collectors.toList())
                )
                .build();
    }

    public OverpaymentRos fromDto(OverpaymentRosDto dto) {
        return OverpaymentRos.builder()
                .id(dto.getId())
                .is_id(dto.getIsId())
                .doc(dto.getDoc())
                .close_date(dto.getClose_date())
                .spe(dto.getSpe())
                .sroks(dto.getSroks())
                .srokpo(dto.getSrokpo())
                .vinap(dto.getVinap())
                .docdv(dto.getDocdv())
                .vidVpl(vidVplRosMapper.fromDto(dto.getVidVpl()))
                .build();
    }
}
