package ru.pfr.overpayments.model.overpayment.entity;

import lombok.*;
import ru.pfr.overpayments.model.annotations.snils.CheckSNILS;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class Pensioner extends Citizen {

    @OneToMany(mappedBy = "pensioner",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private List<Overpayment> overpayments = new ArrayList<>();

    public void addOverpayment(Overpayment overpayment) {
        this.overpayments.add(overpayment);
        overpayment.setPensioner(this);
    }

    public void setAllOverpayment(List<Overpayment> overpayments) {
        for (Overpayment overpayment :
                overpayments) {
            addOverpayment(overpayment);
        }
    }

    public void removeOverpayment(Overpayment overpayment) {
        this.overpayments.remove(overpayment);
        overpayment.setPensioner(null);
    }

    @Builder
    public Pensioner(Long id, String idRos, String snils, String surname, String name, String patronymic, String adrreg, String pol, LocalDate rdat, String tel, LocalDate dsm, District district, List<Overpayment> overpayments) {
        super(id, idRos, snils, surname, name, patronymic, adrreg, pol, rdat, tel, dsm, district);
        this.overpayments = overpayments;
    }
}
