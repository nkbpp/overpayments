package ru.pfr.overpayments.model.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.pfr.overpayments.model.overpayment.entity.Overpayment;
import ru.pfr.overpayments.model.ros.entity.OverpaymentRosParent;
import ru.pfr.overpayments.model.ros.entity.UderRos;
import ru.pfr.overpayments.model.ros.entity.VidVplRos;
import ru.pfr.overpayments.model.ros.entity.VozPereRos;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class FullOverpayment extends OverpaymentRosParent {

    private Overpayment overpayment;

    @Builder
    public FullOverpayment(Long is_id, String id, String doc, LocalDate docdv, LocalDate sroks, LocalDate srokpo, LocalDate close_date, Double spe, Boolean vinap, VidVplRos vidVpl, List<UderRos> uderRos, List<VozPereRos> vozPereRos, Overpayment overpayment) {
        super(is_id, id, doc, docdv, sroks, srokpo, close_date, spe, vinap, vidVpl, uderRos, vozPereRos);
        this.overpayment = overpayment;
    }
}

//176-028-331 64