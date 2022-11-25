package ru.pfr.overpayments.service.overpayment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.DocumentsRepository;
import ru.pfr.overpayments.model.overpayment.entity.Documents;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class DocumentsService {

    private final DocumentsRepository repository;

    public Documents findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Documents> findAll() {
        return repository.findAll();
    }

    public void update(Documents documents) {
        repository.save(documents);
    }

    public void save(Documents documents) {
        repository.save(documents);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
