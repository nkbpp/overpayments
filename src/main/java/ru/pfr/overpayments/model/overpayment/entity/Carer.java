package ru.pfr.overpayments.model.overpayment.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;


@Setter
@Getter
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Carer extends Citizen {

    @OneToOne(mappedBy="carer",
            fetch = FetchType.LAZY,
            orphanRemoval = true, //при удалении из Overpayment удаляется из базы
            cascade = CascadeType.ALL)
    private Overpayment overpayment;

    @Builder
    public Carer(Long id, String idRos, String snils, String surname, String name, String patronymic, String adrreg, String pol, LocalDate rdat, String tel, LocalDate dsm, District district, Overpayment overpayment) {
        super(id, idRos, snils, surname, name, patronymic, adrreg, pol, rdat, tel, dsm, district);
        this.overpayment = overpayment;
    }
}
