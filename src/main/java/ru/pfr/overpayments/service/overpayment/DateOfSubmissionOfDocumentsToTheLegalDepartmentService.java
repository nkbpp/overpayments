package ru.pfr.overpayments.service.overpayment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.pfr.overpayments.jpaRepository.overpayment.DateOfSubmissionOfDocumentsToTheLegalDepartmentRepository;

@Service
@RequiredArgsConstructor
@Transactional(transactionManager = "overpaymentsTransactionManager")
public class DateOfSubmissionOfDocumentsToTheLegalDepartmentService {

    private final DateOfSubmissionOfDocumentsToTheLegalDepartmentRepository repository;

}
