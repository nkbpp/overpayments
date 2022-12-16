package ru.pfr.overpayments.model.ros.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.Formula;

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

    @Column(name = "DOC", insertable = false, updatable = false)
    private String doc;

    @Column(name = "ID", insertable = false, updatable = false)
    private String idRos;

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

    @Column(name = "NN", insertable = false, updatable = false)
    private Integer nn;

    @Formula(
            value = "(" +
                    "SELECT sum(t0.AMOUNT) " +
                    "FROM PAYSUM.PAY t0 INNER JOIN PAYSUM.UDPAY t1 ON t0.id = t1.PAY_ID AND t0.re = t1.re AND t0.ra = t1.ra " +
                    "INNER JOIN PAYSUM.EXECUTIVE_DOC t2 ON t1.DOC_ID = t2.id AND t1.re = t2.re AND t1.ra = t2.ra " +
                    "WHERE " +
                    "t2.MAN_ID = ID AND " +
                    "t0.MONTH = MES AND " +
                    "t0.YEAR = GOD AND " +
                    "t2.DOC = DOC AND " +
                    "t2.NN = NN AND " +
                    "t0.PARENT_ID IS NULL AND " +
                    "t0.ACTION_TYPE in (12,13) " +
                    "GROUP BY t0.month, t0.YEAR, t2.DOC " +
                    ")" //ВАЖНО в аннотации QUERY использовать не SQL а HQL
            /*value = "(SELECT count(u.id) FROM VPL.UDER u where u.GOD = GOD )"*/
    )
    private Double summa; //сумма которую я получил

    @Formula(
            value = "(" +
                    "SELECT  sum(t0.AMOUNT) " +
                    "FROM PAYSUM.PAY t0 INNER JOIN PAYSUM.UDPAY t1 ON t0.id = t1.PAY_ID AND t0.re = t1.re AND t0.ra = t1.ra " +
                    "INNER JOIN PAYSUM.EXECUTIVE_DOC t2 ON t1.DOC_ID = t2.id AND t1.re = t2.re AND t1.ra = t2.ra " +
                    "WHERE " +
                    "t2.MAN_ID = ID AND " +
                    "t0.MONTH = MES AND " +
                    "t0.YEAR = GOD AND " +
                    "t2.DOC = DOC AND " +
                    "t2.NN = NN AND " +
                    "t0.PARENT_ID IS NULL AND " +
                    "t0.ACTION_TYPE in (85) " +
                    "GROUP BY t0.month, t0.YEAR, t2.DOC " +
                    ")"//ВАЖНО в аннотации QUERY использовать не SQL а HQL
            /*value = "(SELECT count(u.id) FROM VPL.UDER u where u.GOD = GOD )"*/
    )
    private Double summaP; //сумма погашение

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "ID", //название столбца в родителе
                    referencedColumnName = "ID" //название столбца в текущей таблице
            ),
            @JoinColumn(name = "DOC", //название столбца в родителе
                    referencedColumnName = "DOC" //название столбца в текущей таблице
            ),
            @JoinColumn(name = "NN", //название столбца в родителе
                    referencedColumnName = "NN" //название столбца в текущей таблице
            )
    })
    private OverpaymentRos overpaymentRos;

    @JsonIgnore
    public OverpaymentRos getOverpaymentRos() {
        return overpaymentRos;
    }
}
