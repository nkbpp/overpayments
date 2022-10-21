package ru.pfr.overpayments.jpaRepository.overpayment.referenceBook;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.SpecificationOfTheReasonsForOverpayments;

import java.util.Optional;

@Repository
public interface SpecificationOfTheReasonsForOverpaymentsRepository extends JpaRepository<SpecificationOfTheReasonsForOverpayments, String> {

    Optional<SpecificationOfTheReasonsForOverpayments> findById(Long id);
    void deleteById(Long id);
}
