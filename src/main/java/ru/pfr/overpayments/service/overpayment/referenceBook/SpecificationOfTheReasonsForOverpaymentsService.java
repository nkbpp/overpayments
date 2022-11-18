package ru.pfr.overpayments.service.overpayment.referenceBook;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsRepository;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.SpecificationOfTheReasonsForOverpayments;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(transactionManager="overpaymentsTransactionManager")
public class SpecificationOfTheReasonsForOverpaymentsService {

    private final SpecificationOfTheReasonsForOverpaymentsRepository repository;

    public SpecificationOfTheReasonsForOverpaymentsService(SpecificationOfTheReasonsForOverpaymentsRepository repository) {
        this.repository = repository;
    }

    public SpecificationOfTheReasonsForOverpayments findById(Long id){
        return repository.findById(id).orElse(null);
    }
    public List<SpecificationOfTheReasonsForOverpayments> findAll(){
        return repository.findAll();
    }

    public List<SpecificationOfTheReasonsForOverpayments> findAll(int pagination, int col){
        return cutTheList(repository.findAll(), pagination, col);
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

    private List<SpecificationOfTheReasonsForOverpayments> cutTheList(List<SpecificationOfTheReasonsForOverpayments> lists, int pagination, int col) {
        List<SpecificationOfTheReasonsForOverpayments> objs = new ArrayList<>();

        int start = col*(pagination-1);
        int end = start + col;

        for (int i = start; i < end && i<lists.size() ; i++) {
            objs.add(lists.get(i));
        }
        return objs;
    }

}
