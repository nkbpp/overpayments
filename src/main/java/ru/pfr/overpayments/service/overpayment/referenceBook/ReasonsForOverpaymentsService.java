package ru.pfr.overpayments.service.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.referenceBook.ReasonsForOverpaymentsRepository;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.ReasonsForOverpayments;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class ReasonsForOverpaymentsService {

    private final ReasonsForOverpaymentsRepository repository;

    public ReasonsForOverpayments findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<ReasonsForOverpayments> findAll() {
        return repository.findAll();
    }

    public List<ReasonsForOverpayments> findAll(Pageable pageable) {
        return repository.findAll(pageable).getContent();
    }

    public void update(ReasonsForOverpayments reasonsForOverpayments) {
        var old = findById(
                reasonsForOverpayments.getId()
        ).getSpecificationOfTheReasonsForOverpayments();
        reasonsForOverpayments.setAllSpecificationOfTheReasonsForOverpayments(old);
        repository.save(reasonsForOverpayments);
    }

    public void save(ReasonsForOverpayments reasonsForOverpayments) {
        repository.save(reasonsForOverpayments);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
