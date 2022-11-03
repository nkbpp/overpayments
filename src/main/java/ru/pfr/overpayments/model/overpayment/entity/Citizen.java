package ru.pfr.overpayments.model.overpayment.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.pfr.overpayments.model.annotations.fio.CustomDateSerializerRu;
import ru.pfr.overpayments.model.annotations.snils.CheckSNILS;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class Citizen {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id cannot be null")
    private Long id;

    @NotNull(message = "id cannot be null")
    private String idRos;

    @CheckSNILS
    private String snils;

    private String surname;

    private String name;

    private String patronymic;

    private String adrreg;

    @JsonSerialize(using = CustomDateSerializerRu.class)
    private LocalDate rdat;

    private String tel;

    @JsonSerialize(using = CustomDateSerializerRu.class)
    private LocalDate dsm; //дата смерти

    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private District district;

    public Citizen(String idRos, String snils, String surname, String name, String patronymic, String adrreg, LocalDate rdat, String tel, LocalDate dsm, District district) {
        this.idRos = idRos;
        this.snils = snils;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.adrreg = adrreg;
        this.rdat = rdat;
        this.tel = tel;
        this.dsm = dsm;
        this.district = district;
    }
}
