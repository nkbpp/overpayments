package ru.pfr.overpayments.service.overpayment.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.referenceBook.DepartmentRepository;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.Department;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class DepartmentService {

    private final DepartmentRepository repository;

    public Department findById(Long id) {
        return repository.findById(id).orElse(null);
    }

    public List<Department> findAll() {
        return repository.findAll();
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

}
