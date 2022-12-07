package ru.pfr.overpayments.model.overpayment.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateDeserializerRuAndEnOrNull;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateSerializerRu;

import java.time.LocalDate;

@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DateOfSubmissionOfDocumentsToTheLegalDepartmentDto {

    private Long id;

    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate transferDate; //Дата передачи

    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    @JsonDeserialize(using = CustomLocalDateDeserializerRuAndEnOrNull.class)
    private LocalDate returnDate; //Дата возврата

}
