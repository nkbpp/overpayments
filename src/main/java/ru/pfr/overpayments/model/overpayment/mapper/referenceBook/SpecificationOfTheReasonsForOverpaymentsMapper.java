package ru.pfr.overpayments.model.overpayment.mapper.referenceBook;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.SpecificationOfTheReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.SpecificationOfTheReasonsForOverpayments;


@Component
public class SpecificationOfTheReasonsForOverpaymentsMapper {

    public SpecificationOfTheReasonsForOverpaymentsDto toDto(SpecificationOfTheReasonsForOverpayments obj) {
        return SpecificationOfTheReasonsForOverpaymentsDto.builder()
                .id(obj.getId())
                .specificationOfTheReasonsForOverpayments(obj.getSpecificationOfTheReasonsForOverpayments())
                .build();
    }

    public SpecificationOfTheReasonsForOverpayments fromDto(SpecificationOfTheReasonsForOverpaymentsDto dto) {
        return SpecificationOfTheReasonsForOverpayments.builder()
                .id(dto.getId())
                .specificationOfTheReasonsForOverpayments(dto.getSpecificationOfTheReasonsForOverpayments())
                .build();
    }

}
