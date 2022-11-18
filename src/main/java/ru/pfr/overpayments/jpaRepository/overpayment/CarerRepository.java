package ru.pfr.overpayments.jpaRepository.overpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.overpayment.entity.Carer;

@Repository
public interface CarerRepository extends JpaRepository<Carer, String> {

    void deleteById(Long id);

}
