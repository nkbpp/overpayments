package ru.pfr.overpayments.model.ros.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "VOZPERE", schema = "VPL")
public class VozPereRos {

    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride( name = "id", column = @Column(name = "ID")),
            @AttributeOverride( name = "god", column = @Column(name = "GOD")),
            @AttributeOverride( name = "mes", column = @Column(name = "MES")),
            @AttributeOverride( name = "nn", column = @Column(name = "NN")),
            @AttributeOverride( name = "recId", column = @Column(name = "REC_ID")),
            @AttributeOverride( name = "doc", column = @Column(name = "DOC"))
    })
    private VozPereRosId vozPereRosId;

    @Column(name = "S")
    private Double s; //сумма возврата переплаты

    @Column(name = "PPDATE")
    private LocalDate ppdate;

    @Column(name = "PPNUM")
    private String ppnum;

    @Column(name = "NN", insertable = false, updatable = false)
    private Integer nn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name="ID", //название столбца в родителе
                    referencedColumnName="ID", //название столбца в текущей таблице
                    insertable = false, updatable = false
            ),
            @JoinColumn(name="DOC", //название столбца в родителе
                    referencedColumnName="DOC", //название столбца в текущей таблице
                    insertable = false, updatable = false
            ),
            @JoinColumn(name="NN", //название столбца в родителе
                    referencedColumnName="NN", //название столбца в текущей таблице
                    insertable = false, updatable = false
            )
    })
    private OverpaymentRos overpaymentRos;

    @JsonIgnore
    public OverpaymentRos getOverpaymentRos() {
        return overpaymentRos;
    }

}
