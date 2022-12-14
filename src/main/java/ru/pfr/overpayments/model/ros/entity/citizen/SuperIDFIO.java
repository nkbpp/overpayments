package ru.pfr.overpayments.model.ros.entity.citizen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
//@SuperBuilder
@MappedSuperclass
public class SuperIDFIO extends SuperID{

    @NotNull(message = "surname cannot be null")
    @Column(name = "FA")
    private String surname;
    @NotNull(message = "name cannot be null")
    @Column(name = "IM")
    private String name;
    @NotNull(message = "patronymic cannot be null")
    @Column(name = "OT")
    private String patronymic;
    @Column(name = "POL")
    private String pol;

    public SuperIDFIO(@NotNull(message = "id cannot be null") String id, String surname, String name, String patronymic, String pol) {
        super(id);
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.pol = pol;
    }
}
