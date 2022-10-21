package ru.pfr.overpayments.service.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.referenceBook.DepartmentRepository;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.Department;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager="overpaymentsTransactionManager")
public class DepartmentService {

    private final DepartmentRepository repository;

    public Department findById(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<Department> findAll(){
        return repository.findAll();
    }

    public List<Department> findAll(int pagination, int col){
        return cutTheList(repository.findAll(), pagination, col);
    }

    public void update(Department department) {
        repository.save(department);
    }
    public void save(Department department) {
        repository.save(department);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    private List<Department> cutTheList(List<Department> lists, int pagination, int col) {
        List<Department> objs = new ArrayList<>();

        int start = col*(pagination-1);
        int end = start + col;

        for (int i = start; i < end && i<lists.size() ; i++) {
            objs.add(lists.get(i));
        }
        return objs;
    }

}
