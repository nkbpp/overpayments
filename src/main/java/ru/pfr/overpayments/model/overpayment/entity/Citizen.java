package ru.pfr.overpayments.model.overpayment.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.github.petrovich4j.Case;
import com.github.petrovich4j.Gender;
import com.github.petrovich4j.NameType;
import com.github.petrovich4j.Petrovich;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.pfr.overpayments.model.annotations.fio.CustomLocalDateSerializerRu;
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

    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
    private LocalDate rdat;

    private String tel;

    @JsonSerialize(using = CustomLocalDateSerializerRu.class)
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

    public String getSurname(Case aCase) {
        if(aCase == null){
            return surname;
        }
        Petrovich petrovich = new Petrovich();
        return petrovich.say(
                surname, NameType.LastName,
                petrovich.gender(surname, Gender.Both),
                Case.Prepositional
        );
    }

    public String getName(Case aCase) {
        if(aCase == null){
            return name;
        }
        Petrovich petrovich = new Petrovich();
        return petrovich.say(
                name, NameType.LastName,
                petrovich.gender(name, Gender.Both),
                Case.Prepositional
        );
    }

    public String getPatronymic(Case aCase) {
        if(aCase == null){
            return patronymic;
        }
        Petrovich petrovich = new Petrovich();
        return petrovich.say(
                patronymic, NameType.LastName,
                petrovich.gender(patronymic, Gender.Both),
                Case.Prepositional
        );
    }

    public String getInitials() {
        return name.substring(0,1).toUpperCase() + "." +
                patronymic.substring(0,1).toUpperCase() + ".";
    }
}
