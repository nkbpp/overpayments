package ru.pfr.overpayments.model.ros.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "IS", schema = "VPL")
public class OverpaymentRos extends OverpaymentRosParent {
    @Builder
    public OverpaymentRos(Long is_id, String id, String doc, Integer nn, LocalDate docdv, LocalDate sroks, LocalDate srokpo, LocalDate close_date, Double spe, Boolean vinap, VidVplRos vidVpl, List<UderRos> uderRos, List<VozPereRos> vozPereRos) {
        super(is_id, id, doc, nn, docdv, sroks, srokpo, close_date, spe, vinap, vidVpl, uderRos, vozPereRos);
    }
}

//176-028-331 64