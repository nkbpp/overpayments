package ru.pfr.overpayments.model.dto;

import lombok.*;
import ru.pfr.overpayments.model.overpayment.dto.DistrictDto;
import ru.pfr.overpayments.model.overpayment.dto.PensionerDto;
import ru.pfr.overpayments.model.overpayment.entity.District;

import java.util.List;
import java.util.Set;
import java.util.SortedSet;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullDistrictDto  implements Comparable<FullDistrictDto> {

    private DistrictDto district;

    private SortedSet<FullStatisticsDto> statistics;

    @Override
    public int compareTo(FullDistrictDto o) {
        return this.district.getKod().compareTo(o.getDistrict().getKod());
    }

}
