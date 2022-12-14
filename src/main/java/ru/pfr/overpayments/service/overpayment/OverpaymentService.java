package ru.pfr.overpayments.service.overpayment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.OverpaymentRepository;
import ru.pfr.overpayments.model.overpayment.entity.Overpayment;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class OverpaymentService {

    private final OverpaymentRepository repository;

    private final CarerService carerService;

    public void update(Overpayment overpayment) {
        save(overpayment);
    }

    public void save(Overpayment overpayment) {
        if (overpayment.getLegalDepartment() != null) {
            for (var o :
                    overpayment.getLegalDepartment()) {
                o.setOverpayment(overpayment);
            }
        }
        if (overpayment.getCarer() != null) {
            overpayment.getCarer().setOverpayment(overpayment);
        }
        repository.save(overpayment);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public Overpayment findByIsId(Long idIs) {
        return repository.findByIsId(idIs).orElse(null);
    }

    public List<Overpayment> findAll(){
        return repository.findAll();
    }
}
