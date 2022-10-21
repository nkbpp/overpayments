package ru.pfr.overpayments.service.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.referenceBook.ReasonsForOverpaymentsRepository;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.ReasonsForOverpayments;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager="overpaymentsTransactionManager")
public class ReasonsForOverpaymentsService {

    private final ReasonsForOverpaymentsRepository repository;

    public ReasonsForOverpayments findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<ReasonsForOverpayments> findAll(){
        return repository.findAll();
    }

    public List<ReasonsForOverpayments> findAll(int pagination, int col){
        return cutTheList(repository.findAll(), pagination, col);
    }

    public void update(ReasonsForOverpayments reasonsForOverpayments) {
        repository.save(reasonsForOverpayments);
    }
    public void save(ReasonsForOverpayments reasonsForOverpayments) {
        repository.save(reasonsForOverpayments);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private List<ReasonsForOverpayments> cutTheList(List<ReasonsForOverpayments> lists, int pagination, int col) {
        List<ReasonsForOverpayments> objs = new ArrayList<>();

        int start = col*(pagination-1);
        int end = start + col;

        for (int i = start; i < end && i<lists.size() ; i++) {
            objs.add(lists.get(i));
        }
        return objs;
    }

}
