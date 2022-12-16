package ru.pfr.overpayments.model.dto;

import lombok.*;
import ru.pfr.overpayments.model.entity.FullOverpayment;
import ru.pfr.overpayments.model.overpayment.dto.OverpaymentDto;
import ru.pfr.overpayments.model.overpayment.dto.PensionerDto;
import ru.pfr.overpayments.model.ros.dto.ParentOverpaymentRosDto;
import ru.pfr.overpayments.model.ros.dto.UderRosDto;
import ru.pfr.overpayments.model.ros.dto.VidVplRosDto;
import ru.pfr.overpayments.model.ros.dto.VozPereRosDto;

import java.time.LocalDate;
import java.util.List;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullPensionerDto  {

    private PensionerDto pensioner;

    private List<FullOverpaymentDto> overpayment;

}
