package ru.pfr.overpayments.model.entity;

import lombok.*;
import ru.pfr.overpayments.model.overpayment.dto.PensionerDto;
import ru.pfr.overpayments.model.overpayment.entity.Overpayment;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;
import ru.pfr.overpayments.model.ros.entity.OverpaymentRosParent;
import ru.pfr.overpayments.model.ros.entity.UderRos;
import ru.pfr.overpayments.model.ros.entity.VidVplRos;
import ru.pfr.overpayments.model.ros.entity.VozPereRos;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FullPensioner {

    private Pensioner pensioner;

    private List<FullOverpayment> overpayment;
}

//176-028-331 64