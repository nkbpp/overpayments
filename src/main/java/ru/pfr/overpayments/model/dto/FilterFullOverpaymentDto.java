package ru.pfr.overpayments.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateDeserializerRuAndEnOrNull;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.SpecificationOfTheReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.entity.CustomDeserializerDistrictById;
import ru.pfr.overpayments.model.overpayment.entity.District;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.CustomDeserializerReasonsForOverpaymentsById;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.CustomDeserializerSpecificationOfTheReasonsForOverpaymentsById;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class FilterFullOverpaymentDto {

    @JsonDeserialize(using = CustomDeserializerDistrictById.class)
    private District numDistrict;

    @JsonDeserialize(using = CustomDeserializerReasonsForOverpaymentsById.class)
    private ReasonsForOverpaymentsDto reasonsForOverpayments;

    @JsonDeserialize(using = CustomDeserializerSpecificationOfTheReasonsForOverpaymentsById.class)
    private SpecificationOfTheReasonsForOverpaymentsDto selectSpecificationOfTheReasonsForOverpayments;

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateDetectionS; //Дата выявления c

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateDetectionPo; //Дата выявления по

    private Double sumS; //Cумма переплаты c

    private Double sumPo; //Cумма переплаты по

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateWriteOffS; //Дата списания c

    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateWriteOffPo; //Дата списания по

}
