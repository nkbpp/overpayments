package ru.pfr.overpayments.jpaRepository.overpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.overpayment.entity.Documents;

import java.util.Optional;

@Repository
public interface DocumentsRepository extends JpaRepository<Documents, String> {

    Optional<Documents> findById(Long id);

    void deleteById(Long id);

}
