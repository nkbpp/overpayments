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

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pensioner_id")
    private Pensioner pensioner;

    @JsonIgnore
    public Pensioner getPensioner() {
        return pensioner;
    }


    @Builder
    public Overpayment(String idRos, Long isId, Boolean zajav, String comment, Department department, ReasonsForOverpayments reasonsForOverpayments, SpecificationOfTheReasonsForOverpayments specificationOfTheReasonsForOverpayments, Carer carer, Pensioner pensioner) {
        this.idRos = idRos;
        this.isId = isId;
        this.zajav = zajav;
        this.comment = comment;
        this.department = department;
        this.reasonsForOverpayments = reasonsForOverpayments;
        this.specificationOfTheReasonsForOverpayments = specificationOfTheReasonsForOverpayments;
        this.carer = carer;
        this.pensioner = pensioner;
    }
/*    @OneToMany(mappedBy = "overpaymentOverpayment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CarerOverpayment> carer = new ArrayList<>();


    public void addCarer(CarerOverpayment carerOverpayment) {
        this.carer.add(carerOverpayment);
        carerOverpayment.setOverpayment(this);
    }

    public void setAllCarer(List<CarerOverpayment> carerOverpayments) {
        for (CarerOverpayment c :
                carerOverpayments) {
            addCarer(c);
        }
    }

    public void removeCarer(CarerOverpayment carerOverpayment) {
        this.carer.remove(carerOverpayment);
        carerOverpayment.setOverpayment(null);
    }*/

}
