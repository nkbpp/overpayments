package ru.pfr.overpayments.model.overpayment.mapper;

import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.DateOfSubmissionOfDocumentsToTheLegalDepartmentDto;
import ru.pfr.overpayments.model.overpayment.entity.DateOfSubmissionOfDocumentsToTheLegalDepartment;

@Component
public class DateOfSubmissionOfDocumentsToTheLegalDepartmentMapper {

    public DateOfSubmissionOfDocumentsToTheLegalDepartmentDto toDto(DateOfSubmissionOfDocumentsToTheLegalDepartment obj) {
        return DateOfSubmissionOfDocumentsToTheLegalDepartmentDto.builder()
                .id(obj.getId())
                .transferDate(obj.getTransferDate())
                .returnDate(obj.getReturnDate())
                .build();
    }


    public DateOfSubmissionOfDocumentsToTheLegalDepartment fromDto(DateOfSubmissionOfDocumentsToTheLegalDepartmentDto dto) {
        return DateOfSubmissionOfDocumentsToTheLegalDepartment.builder()
                .id(dto.getId())
                .transferDate(dto.getTransferDate())
                .returnDate(dto.getReturnDate())
                .build();
    }

}
