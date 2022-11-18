package ru.pfr.overpayments.model.overpayment.dto.referenceBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.overpayment.dto.DocumentsDto;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SpecificationOfTheReasonsForOverpaymentsDto {

    private Long id;

    private String specificationOfTheReasonsForOverpayments;

    private Long idReasonsForOverpayment;

    private DocumentsDto documentPensioner;

    private DocumentsDto documentCarer;

}
