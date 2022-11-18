package ru.pfr.overpayments.model.ros.entity;

import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@MappedSuperclass
public class OverpaymentRosParent implements Serializable {

    @Id
    @Column(name = "IS_ID", updatable = false, nullable = false)
    @NotNull(message = "id cannot be null")
    private Long is_id;

    @NotNull(message = "id cannot be null")
    private String id;

    @Column(name = "DOC")
    private String doc; //номер документа

    @Column(name = "DOCDV")
    private LocalDate docdv; //дата выдачи исполнит.док-та

    @Column(name = "SROKS")
    private LocalDate sroks; //дата начала выплат

    @Column(name = "SROKPO")
    private LocalDate srokpo; //дата окончания удержания

    @Column(name = "CLOSE_DATE")
    private LocalDate close_date; //дата закрытия удержания

    @Column(name = "SPE")
    private Double spe; //Общая установленная к удержанию сумма по исполнительному документу (со всего дохода или только из пенсии)

    @Column(name = "VINAP")
    private Boolean vinap; //Вина пенсионера

    @OneToOne(optional=true, cascade=CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn (name="UBRAZ")
    private VidVplRos vidVpl; //вид выплаты

    @OneToMany(targetEntity=UderRos.class, mappedBy = "overpaymentRos", //Свойство mappedBy — это то, что мы используем, чтобы сообщить Hibernate, какую переменную мы используем для представления родительского класса в нашем дочернем классе.
            orphanRemoval = true//,
            //fetch = FetchType.EAGER
    )
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<UderRos> uderRos;

    @OneToMany(targetEntity=VozPereRos.class, mappedBy = "overpaymentRos", //Свойство mappedBy — это то, что мы используем, чтобы сообщить Hibernate, какую переменную мы используем для представления родительского класса в нашем дочернем классе.
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<VozPereRos> vozPereRos;

    public OverpaymentRosParent(Long is_id, String id, String doc, LocalDate docdv, LocalDate sroks, LocalDate srokpo, LocalDate close_date, Double spe, Boolean vinap, VidVplRos vidVpl, List<UderRos> uderRos, List<VozPereRos> vozPereRos) {
        this.is_id = is_id;
        this.id = id;
        this.doc = doc;
        this.docdv = docdv;
        this.sroks = sroks;
        this.srokpo = srokpo;
        this.close_date = close_date;
        this.spe = spe;
        this.vinap = vinap;
        this.vidVpl = vidVpl;
        this.uderRos = uderRos;
        this.vozPereRos = vozPereRos;
    }

    /*    @Column(name = "SZA")
    private Double sza; //Осумма задолженности

    @Column(name = "MPU")
    private Double mpu; //ежемесячный процент удержания

    @Column(name = "MSU")
    private Double msu; //ежемесячная сумма удержания*/
}

//176-028-331 64