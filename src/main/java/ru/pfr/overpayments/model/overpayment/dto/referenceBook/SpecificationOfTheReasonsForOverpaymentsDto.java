package ru.pfr.overpayments.model.overpayment.dto.referenceBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecificationOfTheReasonsForOverpaymentsDto {

    private Long id;

    private String specificationOfTheReasonsForOverpayments;

}
