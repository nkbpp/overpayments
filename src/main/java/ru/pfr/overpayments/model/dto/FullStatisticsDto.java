package ru.pfr.overpayments.model.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import ru.pfr.overpayments.model.annotations.OkrugSerializer;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;

import java.util.Set;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullStatisticsDto implements Comparable<FullStatisticsDto> {

    private String reason;

    private Integer v1;

    @JsonSerialize(using = OkrugSerializer.class)
    private Double d1;

    {
        v1 = 0;
        d1 = 0D;
    }
    public FullStatisticsDto(String reason) {
        this.reason = reason;
    }

    @Override
    public int compareTo(FullStatisticsDto o) {
        return this.reason.compareTo(o.getReason());
    }
}
