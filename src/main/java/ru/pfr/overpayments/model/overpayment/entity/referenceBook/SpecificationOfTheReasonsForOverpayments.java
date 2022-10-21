package ru.pfr.overpayments.model.overpayment.entity.referenceBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "specificationOfTheReasonsForOverpayments")
public class SpecificationOfTheReasonsForOverpayments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "specificationOfTheReasonsForOverpayments")
    private String specificationOfTheReasonsForOverpayments;

}
