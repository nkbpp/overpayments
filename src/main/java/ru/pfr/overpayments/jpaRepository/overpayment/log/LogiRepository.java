package ru.pfr.overpayments.jpaRepository.overpayment.log;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.pfr.overpayments.model.overpayment.entity.log.Logi;


public interface LogiRepository extends JpaRepository<Logi, Long> {
}

