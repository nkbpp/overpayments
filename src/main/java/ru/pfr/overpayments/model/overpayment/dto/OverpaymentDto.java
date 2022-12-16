package ru.pfr.overpayments.model.overpayment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.annotations.OkrugSerializer;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateDeserializerRuAndEnOrNull;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateSerializerRu;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.DepartmentDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.SpecificationOfTheReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.entity.DateOfSubmissionOfDocumentsToTheLegalDepartment;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate writeOffProtocolDate;
    private String writeOffOrderNumber;
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate writeOffOrderDate;
    @JsonSerialize(using = OkrugSerializer.class)
    private Double writeOffSum;

    //взыскание
    private Boolean controlUFSSP;
    private Boolean theFactThatTheDebtorHasAJob;
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateOfCourtDecision; //Дата решение суда
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateUFSSP; //Дата передачи исполнительного документа в УФССП
    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate dateUVPSV; //Дата передачи исполнительного документа в управление выплаты пенсий и социальных выплат
    private List<DateOfSubmissionOfDocumentsToTheLegalDepartmentDto> legalDepartment = new ArrayList<>();

}
