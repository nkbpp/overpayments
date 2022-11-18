package ru.pfr.overpayments.model.overpayment.entity.referenceBook;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import ru.pfr.overpayments.model.overpayment.entity.Documents;

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

    @ManyToOne
    @JoinColumn(name = "reasonsForOverpayments_id")
    @LazyCollection(LazyCollectionOption.TRUE)
    private ReasonsForOverpayments reasonsForOverpayments;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn (name="documentPensioner_id")
    private Documents documentPensioner;

    @ManyToOne
    @LazyCollection(LazyCollectionOption.TRUE)
    @JoinColumn (name="documentCarer_id")
    private Documents documentCarer;
    @JsonIgnore
    public ReasonsForOverpayments getReasonsForOverpayments() {
        return reasonsForOverpayments;
    }




}
