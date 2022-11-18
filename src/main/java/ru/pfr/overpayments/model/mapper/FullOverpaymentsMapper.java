package ru.pfr.overpayments.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.mapper.OverpaymentMapper;
import ru.pfr.overpayments.model.dto.FullOverpaymentDto;
import ru.pfr.overpayments.model.entity.FullOverpayment;
import ru.pfr.overpayments.model.ros.mapper.UderRosMapper;
import ru.pfr.overpayments.model.ros.mapper.VidVplRosMapper;
import ru.pfr.overpayments.model.ros.mapper.VozPereRosMapper;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class FullOverpaymentsMapper {

    private final VidVplRosMapper vidVplRosMapper;
    private final UderRosMapper uderRosMapper;
    private final VozPereRosMapper vozPereRosMapper;
    private final OverpaymentMapper overpaymentMapper;

    public FullOverpaymentDto toDto(
            FullOverpayment obj
            //OverpaymentRos obj, Overpayment obj2
    ) {
        return FullOverpaymentDto.builder()
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
                .overpayment(overpaymentMapper.toDto(obj.getOverpayment()))
                .build();
    }

/*    public OverpaymentRos fromDto(OverpaymentRosDto dto) {
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
    }*/
}
