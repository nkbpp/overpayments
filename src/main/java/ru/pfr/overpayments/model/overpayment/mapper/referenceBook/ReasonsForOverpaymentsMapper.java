package ru.pfr.overpayments.model.overpayment.mapper.referenceBook;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.ReasonsForOverpayments;

@Component
public class ReasonsForOverpaymentsMapper {

    public ReasonsForOverpaymentsDto toDto(ReasonsForOverpayments obj) {
        return ReasonsForOverpaymentsDto.builder()
                .id(obj.getId())
                .reasonsForOverpayments(obj.getReasonsForOverpayments())
                .build();
    }

    public ReasonsForOverpayments fromDto(ReasonsForOverpaymentsDto dto) {
        return ReasonsForOverpayments.builder()
                .id(dto.getId())
                .reasonsForOverpayments(dto.getReasonsForOverpayments())
                .build();
    }

}
