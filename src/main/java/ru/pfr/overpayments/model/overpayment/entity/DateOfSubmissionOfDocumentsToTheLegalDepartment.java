package ru.pfr.overpayments.model.overpayment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class DateOfSubmissionOfDocumentsToTheLegalDepartment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id cannot be null")
    private Long id;

    private LocalDate transferDate; //Дата передачи

    private LocalDate returnDate; //Дата возврата

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "overpayment_id")
    private Overpayment overpayment;

    @JsonIgnore
    public Overpayment getOverpayment() {
        return overpayment;
    }

}
