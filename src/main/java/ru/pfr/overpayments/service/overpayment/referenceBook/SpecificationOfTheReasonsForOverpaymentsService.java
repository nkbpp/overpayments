package ru.pfr.overpayments.service.overpayment.referenceBook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsRepository;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.SpecificationOfTheReasonsForOverpayments;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class SpecificationOfTheReasonsForOverpaymentsService {

    private final SpecificationOfTheReasonsForOverpaymentsRepository repository;

    public SpecificationOfTheReasonsForOverpaymentsService(SpecificationOfTheReasonsForOverpaymentsRepository repository) {
        this.repository = repository;
    }

    public SpecificationOfTheReasonsForOverpayments findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<SpecificationOfTheReasonsForOverpayments> findAll() {
        return repository.findAll();
    }

    public void update(SpecificationOfTheReasonsForOverpayments specificationOfTheReasonsForOverpayments) {
        /*var newSpecificationOfTheReasonsForOverpayments = findById(
                specificationOfTheReasonsForOverpayments.getId()
        );
        newSpecificationOfTheReasonsForOverpayments.setSpecificationOfTheReasonsForOverpayments(specificationOfTheReasonsForOverpayments.getSpecificationOfTheReasonsForOverpayments());
        newSpecificationOfTheReasonsForOverpayments.setDocumentCarer(specificationOfTheReasonsForOverpayments.getDocumentCarer());
        newSpecificationOfTheReasonsForOverpayments.setDocumentPensioner(specificationOfTheReasonsForOverpayments.getDocumentPensioner());
        repository.save(newSpecificationOfTheReasonsForOverpayments);*/
        repository.save(specificationOfTheReasonsForOverpayments);
    }

    public void save(SpecificationOfTheReasonsForOverpayments specificationOfTheReasonsForOverpayments) {
        repository.save(specificationOfTheReasonsForOverpayments);
    }

    public void delete(Long id) {
        //удалить ссылки на ReasonsForOverpayments
        var r = findById(id);
        r.setReasonsForOverpayments(null);
        save(r);
        repository.deleteById(id);
    }

}
