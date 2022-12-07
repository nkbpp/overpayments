package ru.pfr.overpayments.jpaRepository.overpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pfr.overpayments.model.overpayment.entity.DateOfSubmissionOfDocumentsToTheLegalDepartment;

@Repository
public interface DateOfSubmissionOfDocumentsToTheLegalDepartmentRepository extends JpaRepository<DateOfSubmissionOfDocumentsToTheLegalDepartment, Long> {

}
