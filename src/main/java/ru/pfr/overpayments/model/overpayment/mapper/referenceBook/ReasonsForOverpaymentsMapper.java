package ru.pfr.overpayments.model.overpayment.mapper.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.ReasonsForOverpayments;

import java.util.ArrayList;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReasonsForOverpaymentsMapper {

    private final SpecificationOfTheReasonsForOverpaymentsMapper specificationOfTheReasonsForOverpaymentsMapper;

    public ReasonsForOverpaymentsDto toDto(ReasonsForOverpayments obj) {
        return ReasonsForOverpaymentsDto.builder()
                .id(obj.getId())
                .reasonsForOverpayments(obj.getReasonsForOverpayments())
                .specificationOfTheReasonsForOverpaymentsDtos(
                        obj.getSpecificationOfTheReasonsForOverpayments()
                                .stream()
                                .map(specificationOfTheReasonsForOverpaymentsMapper::toDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

    public ReasonsForOverpayments fromDto(ReasonsForOverpaymentsDto dto) {
        return ReasonsForOverpayments.builder()
                .id(dto.getId())
                .reasonsForOverpayments(dto.getReasonsForOverpayments())
                .specificationOfTheReasonsForOverpayments(
                        dto.getSpecificationOfTheReasonsForOverpaymentsDtos() == null ?
                                new ArrayList<>() :
                        dto.getSpecificationOfTheReasonsForOverpaymentsDtos()
                                .stream()
                                .map(specificationOfTheReasonsForOverpaymentsMapper::fromDto)
                                .collect(Collectors.toList())
                )
                .build();
    }

}
