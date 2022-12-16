package ru.pfr.overpayments.model.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.dto.FullPensionerDto;
import ru.pfr.overpayments.model.entity.FullPensioner;
import ru.pfr.overpayments.model.overpayment.mapper.PensionerMapper;

import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class FullPensionerMapper {

    private final PensionerMapper pensionerMapper;
    private final FullOverpaymentsMapper fullOverpaymentsMapper;

    public FullPensionerDto toDto(
            FullPensioner obj
    ) {
        return FullPensionerDto.builder()
                .pensioner(
                        pensionerMapper.toDto(obj.getPensioner())
                )
                .overpayment(
                        obj.getOverpayment() == null ? null :
                                obj.getOverpayment()
                                        .stream()
                                        .map(fullOverpaymentsMapper::toDto)
                                        .collect(Collectors.toList())
                )
                .build();
    }

}
