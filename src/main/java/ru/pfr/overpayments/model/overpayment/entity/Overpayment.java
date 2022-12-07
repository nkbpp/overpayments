package ru.pfr.overpayments.model.overpayment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.Department;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.ReasonsForOverpayments;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.SpecificationOfTheReasonsForOverpayments;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Overpayment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @NotNull(message = "id cannot be null")
    private Long id;

    @NotNull(message = "id cannot be null")
    private String idRos;

    @NotNull(message = "id cannot be null")
    private Long isId;

    private Boolean zajav; //заявление о добровольном погашении

    private String comment; //заявление о добровольном погашении

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @LazyCollection(LazyCollectionOption.FALSE)
    private Department department;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @LazyCollection(LazyCollectionOption.FALSE)
    private ReasonsForOverpayments reasonsForOverpayments;

    @ManyToOne
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    @LazyCollection(LazyCollectionOption.FALSE)
    private SpecificationOfTheReasonsForOverpayments specificationOfTheReasonsForOverpayments;

    @OneToOne(cascade = CascadeType.ALL)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn (name="carer_id")
    private Carer carer;

    private LocalDate writeOffProtocolDate;

    private String writeOffOrderNumber;

    private LocalDate writeOffOrderDate;

    private Double writeOffSum;

    //взыскание
    private Boolean controlUFSSP;
    private Boolean theFactThatTheDebtorHasAJob;
    private LocalDate dateOfCourtDecision; //Дата решение суда
    private LocalDate dateUFSSP; //Дата передачи исполнительного документа в УФССП
    private LocalDate dateUVPSV; //Дата передачи исполнительного документа в управление выплаты пенсий и социальных выплат
    @OneToMany(mappedBy = "overpayment",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<DateOfSubmissionOfDocumentsToTheLegalDepartment> legalDepartment = new ArrayList<>();

/*    public void addDateOfSubmissionOfDocumentsToTheLegalDepartment(DateOfSubmissionOfDocumentsToTheLegalDepartment legalDepartment) {
        this.legalDepartment.add(legalDepartment);
        legalDepartment.setOverpayment(this);
    }

    public void setAllDateOfSubmissionOfDocumentsToTheLegalDepartment(List<DateOfSubmissionOfDocumentsToTheLegalDepartment> legalDepartment) {
        for (DateOfSubmissionOfDocumentsToTheLegalDepartment dateOfSubmissionOfDocumentsToTheLegalDepartment :
                legalDepartment) {
            addDateOfSubmissionOfDocumentsToTheLegalDepartment(dateOfSubmissionOfDocumentsToTheLegalDepartment);
        }
    }

    public void removeDateOfSubmissionOfDocumentsToTheLegalDepartment(DateOfSubmissionOfDocumentsToTheLegalDepartment legalDepartment) {
        this.legalDepartment.remove(legalDepartment);
        legalDepartment.setOverpayment(null);
    }*/

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pensioner_id")
    private Pensioner pensioner;

    @JsonIgnore
    public Pensioner getPensioner() {
        return pensioner;
    }

}
