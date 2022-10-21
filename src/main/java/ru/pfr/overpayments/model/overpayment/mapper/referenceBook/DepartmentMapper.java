package ru.pfr.overpayments.model.overpayment.mapper.referenceBook;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.DepartmentDto;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.Department;

@Component
public class DepartmentMapper {

    public DepartmentDto toDto(Department obj) {
        return DepartmentDto.builder()
                .id(obj.getId())
                .name(obj.getName())
                .build();
    }

    public Department fromDto(DepartmentDto dto) {
        return Department.builder()
                .id(dto.getId())
                .name(dto.getName())
                .build();
    }

}
