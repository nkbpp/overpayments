package ru.pfr.overpayments.model.dto;

import lombok.*;
import ru.pfr.overpayments.model.overpayment.dto.DistrictDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.ReasonsForOverpaymentsDto;

import java.util.List;
import java.util.Set;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullReasonsDto implements Comparable<ReasonsForOverpaymentsDto> {

    private ReasonsForOverpaymentsDto reason;

    private Set<FullPensionerDto> pensioners;

    @Override
    public int compareTo(ReasonsForOverpaymentsDto o) {
        return this.reason.getId().compareTo(o.getId());
    }

}
