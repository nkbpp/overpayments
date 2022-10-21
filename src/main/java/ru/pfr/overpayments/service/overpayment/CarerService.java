package ru.pfr.overpayments.service.overpayment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.CarerRepository;
import ru.pfr.overpayments.model.overpayment.entity.Carer;


@RequiredArgsConstructor
@Service
@Transactional(transactionManager="overpaymentsTransactionManager")
public class CarerService {

    private final CarerRepository repository;

    public void update(Carer carer) {
        repository.save(carer);
    }

    public void save(Carer carer) {
        repository.save(carer);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }




}
