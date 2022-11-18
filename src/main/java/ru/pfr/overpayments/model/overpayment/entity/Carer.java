package ru.pfr.overpayments.model.overpayment.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@Entity
public class Carer extends Citizen {

    @OneToOne(mappedBy="carer",
            fetch = FetchType.LAZY,
            orphanRemoval = true, //при удалении из Overpayment удаляется из базы
            cascade = CascadeType.ALL)
    private Overpayment overpayment;

    @Builder
    public Carer(String idRos, String snils, String surname, String name, String patronymic, String adrreg, LocalDate rdat, String tel, LocalDate dsm, District district, Overpayment overpayment) {
        super(idRos, snils, surname, name, patronymic, adrreg, rdat, tel, dsm, district);
        this.overpayment = overpayment;
    }

    public Carer(Long id, String idRos, String snils, String surname, String name, String patronymic, String adrreg, LocalDate rdat, String tel, LocalDate dsm, District district, Overpayment overpayment) {
        super(id, idRos, snils, surname, name, patronymic, adrreg, rdat, tel, dsm, district);
        this.overpayment = overpayment;
    }
}
