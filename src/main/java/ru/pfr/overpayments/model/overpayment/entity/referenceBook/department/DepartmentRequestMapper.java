package ru.pfr.overpayments.model.overpayment.entity.referenceBook.department;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.jpaRepository.overpayment.referenceBook.DepartmentRepository;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.department.DepartmentRequest;

import java.util.Optional;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class DepartmentRequestMapper implements Function<DepartmentRequest, Optional<Department>> {

    private final DepartmentRepository repository;

    @Override
    public Optional<Department> apply(DepartmentRequest departmentRequest) {
        Department department;
        if (departmentRequest.id() == null) {
            department = new Department();
        } else {
            var optionalDepartment = repository.findById(departmentRequest.id());
            if (optionalDepartment.isEmpty()) {
                return Optional.empty();
            }
            department = optionalDepartment.get();
        }
        department.name = departmentRequest.name();
        return Optional.of(department);
    }

}
