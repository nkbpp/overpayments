package ru.pfr.overpayments.model.overpayment.entity.referenceBook.department;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.department.DepartmentResponse;

import java.util.function.Function;

@Component
public class DepartmentResponseMapper implements Function<Department, DepartmentResponse> {

    public DepartmentResponse apply(Department department) {
        return new DepartmentResponse(
                department.id,
                department.name
        );
    }

}
