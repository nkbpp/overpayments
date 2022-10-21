package ru.pfr.overpayments.model.ros.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import ru.pfr.overpayments.model.overpayment.entity.Carer;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "UDER", schema = "VPL")
public class UderRos {

    @Id
    @Column(name = "REC_ID")
    private Long recId;

    @Column(name = "GOD")
    private Integer god;

    @Column(name = "MES")
    private Integer mes;

    @Column(name = "U_B")
    private Double ub; //удержано из БЧ

    @Column(name = "U_S")
    private Double us; //удержано из CЧ

    @Column(name = "U_SDDPM")
    private Double uSddpm; //удержание

    @Column(name = "OU_SDDPM")
    private Double ouSddpm; //остаток удержания

    @Column(name = "UDER_PERCENT")
    private Double uderPercent; //процент удержания с твердой суммы

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="ID", //название столбца в родителе
                    referencedColumnName="ID" //название столбца в текущей таблице
            ),
            @JoinColumn(name="DOC", //название столбца в родителе
                    referencedColumnName="DOC" //название столбца в текущей таблице
            )
    })
    private OverpaymentRos overpaymentRos;

    @JsonIgnore
    public OverpaymentRos getOverpaymentRos() {
        return overpaymentRos;
    }
}
