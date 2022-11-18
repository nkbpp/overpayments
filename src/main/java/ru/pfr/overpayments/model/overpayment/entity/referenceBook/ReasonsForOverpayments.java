package ru.pfr.overpayments.model.overpayment.entity.referenceBook;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "reasonsForOverpayments")
public class ReasonsForOverpayments {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reasonsForOverpayments")
    private String reasonsForOverpayments;

    @OneToMany(mappedBy = "reasonsForOverpayments",
            cascade = CascadeType.ALL,
            orphanRemoval = true, //orphanRemoval удаляет конкретизацию из базы при удалении конкретизации из причины
            fetch = FetchType.EAGER)
    private List<SpecificationOfTheReasonsForOverpayments> specificationOfTheReasonsForOverpayments = new ArrayList<>();

    public void addSpecificationOfTheReasonsForOverpayments(SpecificationOfTheReasonsForOverpayments specificationOfTheReasonsForOverpayments) {
        this.specificationOfTheReasonsForOverpayments.add(specificationOfTheReasonsForOverpayments);
        specificationOfTheReasonsForOverpayments.setReasonsForOverpayments(this);
    }

    public void setAllSpecificationOfTheReasonsForOverpayments(List<SpecificationOfTheReasonsForOverpayments> specificationOfTheReasonsForOverpayments) {
        for (SpecificationOfTheReasonsForOverpayments specificationOfTheReasonsForOverpayments1 :
                specificationOfTheReasonsForOverpayments) {
            addSpecificationOfTheReasonsForOverpayments(specificationOfTheReasonsForOverpayments1);
        }
    }

    public void removeSpecificationOfTheReasonsForOverpayments(SpecificationOfTheReasonsForOverpayments specificationOfTheReasonsForOverpayments) {
        this.specificationOfTheReasonsForOverpayments.remove(specificationOfTheReasonsForOverpayments);
        specificationOfTheReasonsForOverpayments.setReasonsForOverpayments(null);
    }

}
