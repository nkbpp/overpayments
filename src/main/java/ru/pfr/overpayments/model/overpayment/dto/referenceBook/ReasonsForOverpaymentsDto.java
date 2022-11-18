package ru.pfr.overpayments.model.overpayment.dto.referenceBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReasonsForOverpaymentsDto {

    private Long id;

    private String reasonsForOverpayments;

    private List<SpecificationOfTheReasonsForOverpaymentsDto> specificationOfTheReasonsForOverpaymentsDtos;

}
