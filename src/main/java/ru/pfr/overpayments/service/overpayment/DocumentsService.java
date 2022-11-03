package ru.pfr.overpayments.service.overpayment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.DistrictRepository;
import ru.pfr.overpayments.jpaRepository.overpayment.DocumentsRepository;
import ru.pfr.overpayments.model.overpayment.entity.District;
import ru.pfr.overpayments.model.overpayment.entity.Documents;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager="overpaymentsTransactionManager")
public class DocumentsService {

    private final DocumentsRepository repository;

    public Documents findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Documents> findAll(){
        return repository.findAll();
    }

    public List<Documents> findAll(int pagination, int col){
        return cutTheList(repository.findAll(), pagination, col);
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

    private List<Documents> cutTheList(List<Documents> lists, int pagination, int col) {
        List<Documents> objs = new ArrayList<>();

        int start = col*(pagination-1);
        int end = start + col;

        for (int i = start; i < end && i<lists.size() ; i++) {
            objs.add(lists.get(i));
        }
        return objs;
    }

}
