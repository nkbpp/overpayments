package ru.pfr.overpayments.model.overpayment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.DepartmentDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.SpecificationOfTheReasonsForOverpaymentsDto;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverpaymentDto {
    private Long idPensioner;
    private String idRos;
    private Long idOverpayment;
    private ReasonsForOverpaymentsDto reasonsForOverpaymentsDto;
    private SpecificationOfTheReasonsForOverpaymentsDto specificationOfTheReasonsForOverpaymentsDto;
    private DepartmentDto departmentDto;
    private String comment;
    private Boolean isApplicationForVoluntaryRedemption;
    private CarerDto carer;
}
