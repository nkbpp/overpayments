package ru.pfr.overpayments.model.ros.entity.citizen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class SuperID {

    /*    @GeneratedValue(generator = "UUID")
        @GenericGenerator(
                name = "UUID",
                strategy = "org.hibernate.id.UUIDGenerator"
        )*/
    @Id
    @Column(name = "ID", updatable = false, nullable = false)
    @NotNull(message = "id cannot be null")
    private String id;

}
