package ru.pfr.overpayments.model.overpayment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.DateOfSubmissionOfDocumentsToTheLegalDepartmentDto;
import ru.pfr.overpayments.model.overpayment.dto.OverpaymentDto;
import ru.pfr.overpayments.model.overpayment.entity.Carer;
import ru.pfr.overpayments.model.overpayment.entity.DateOfSubmissionOfDocumentsToTheLegalDepartment;
import ru.pfr.overpayments.model.overpayment.entity.Overpayment;
import ru.pfr.overpayments.model.overpayment.entity.Pensioner;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.ReasonsForOverpayments;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.SpecificationOfTheReasonsForOverpayments;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.department.Department;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.DepartmentMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.ReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.model.overpayment.mapper.referenceBook.SpecificationOfTheReasonsForOverpaymentsMapper;
import ru.pfr.overpayments.model.ros.entity.citizen.CitizenRos;
import ru.pfr.overpayments.service.overpayment.PensionerService;
import ru.pfr.overpayments.service.overpayment.referenceBook.DepartmentService;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.SpecificationOfTheReasonsForOverpaymentsService;
import ru.pfr.overpayments.service.ros.CitizenRosService;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OverpaymentMapper {

    private final CitizenRosService citizenRosService;
    private final CarerMapper carerMapper;
    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;
    private final ReasonsForOverpaymentsService reasonsForOverpaymentsService;
    private final ReasonsForOverpaymentsMapper reasonsForOverpaymentsMapper;
    private final SpecificationOfTheReasonsForOverpaymentsService specificationOfTheReasonsForOverpaymentsService;
    private final SpecificationOfTheReasonsForOverpaymentsMapper specificationOfTheReasonsForOverpaymentsMapper;
    private final PensionerService pensionerService;
    private final DateOfSubmissionOfDocumentsToTheLegalDepartmentMapper dateOfSubmissionOfDocumentsToTheLegalDepartmentMapper;

    public OverpaymentDto toDto(Overpayment obj) {
        List<DateOfSubmissionOfDocumentsToTheLegalDepartmentDto> legalDepartment = new ArrayList<>();
        if(obj.getLegalDepartment() != null){
            for (var ld :
                    obj.getLegalDepartment()) {
                legalDepartment.add(dateOfSubmissionOfDocumentsToTheLegalDepartmentMapper.toDto(ld));
            }
        }
        return OverpaymentDto.builder()
                .idPensioner(obj.getId())
                .idRos(obj.getIdRos())
                .idOverpayment(obj.getIsId())
                .reasonsForOverpaymentsDto(
                        obj.getReasonsForOverpayments() == null ?
                                null :
                                reasonsForOverpaymentsMapper.toDto(
                                        obj.getReasonsForOverpayments())
                )
                .specificationOfTheReasonsForOverpaymentsDto(
                        obj.getSpecificationOfTheReasonsForOverpayments() == null ?
                                null :
                                specificationOfTheReasonsForOverpaymentsMapper.toDto(
                                        obj.getSpecificationOfTheReasonsForOverpayments())
                )
                .departmentDto(
                        obj.getDepartment() == null ?
                                null :
                                departmentMapper.toDto(obj.getDepartment())
                )
                .isApplicationForVoluntaryRedemption(obj.getZajav())
                .comment(obj.getComment())
                .carer(
                        obj.getCarer() == null ?
                                null :
                        carerMapper.toDto(obj.getCarer())
                )
                .writeOffOrderDate(obj.getWriteOffOrderDate())
                .writeOffOrderNumber(obj.getWriteOffOrderNumber())
                .writeOffProtocolDate(obj.getWriteOffProtocolDate())
                .writeOffSum(obj.getWriteOffSum())
                .controlUFSSP(obj.getControlUFSSP())
                .theFactThatTheDebtorHasAJob(obj.getTheFactThatTheDebtorHasAJob())

                .dateOfCourtDecision(obj.getDateOfCourtDecision())
                .dateUFSSP(obj.getDateUFSSP())
                .dateUVPSV(obj.getDateUVPSV())
                .legalDepartment(legalDepartment)
                .build();
    }


    public Overpayment fromDto(OverpaymentDto dto) {

        Carer carer = null;
        if (!dto.getCarer().getId_ros().equals("")) {
            CitizenRos citizenRos = citizenRosService.findById(dto.getCarer().getId_ros());
            carer = carerMapper.fromDto(citizenRos);
            carer.setAdrreg(dto.getCarer().getAdrreg());
            carer.setTel(dto.getCarer().getTel());
        }

        ReasonsForOverpayments reasonsForOverpayments = dto.getReasonsForOverpaymentsDto().getId() == null ?
                null :
                reasonsForOverpaymentsService.findById(dto.getReasonsForOverpaymentsDto().getId());
        SpecificationOfTheReasonsForOverpayments specificationOfTheReasonsForOverpayments = dto.getSpecificationOfTheReasonsForOverpaymentsDto().getId() == null ?
                null :
                specificationOfTheReasonsForOverpaymentsService.findById(dto.getSpecificationOfTheReasonsForOverpaymentsDto().getId());
        Department department = dto.getDepartmentDto().getId() == null ?
                null :
                departmentService.findById(dto.getDepartmentDto().getId());
        Pensioner pensioner = pensionerService.findById(dto.getIdPensioner());

        List<DateOfSubmissionOfDocumentsToTheLegalDepartment> legalDepartment = new ArrayList<>();
        if(dto.getLegalDepartment() != null){
            for (var ld :
                    dto.getLegalDepartment()) {
                legalDepartment.add(dateOfSubmissionOfDocumentsToTheLegalDepartmentMapper.fromDto(ld));
            }
        }

        return Overpayment.builder()
                .idRos(pensioner.getIdRos())
                .isId(dto.getIdOverpayment())
                .comment(dto.getComment())
                .zajav(dto.getIsApplicationForVoluntaryRedemption())
                .carer(carer)
                .department(department)
                .reasonsForOverpayments(reasonsForOverpayments)
                .specificationOfTheReasonsForOverpayments(specificationOfTheReasonsForOverpayments)
                .pensioner(pensioner)
                .writeOffOrderDate(dto.getWriteOffOrderDate())
                .writeOffOrderNumber(dto.getWriteOffOrderNumber())
                .writeOffProtocolDate(dto.getWriteOffProtocolDate())
                .writeOffSum(dto.getWriteOffSum())
                .controlUFSSP(dto.getControlUFSSP())
                .theFactThatTheDebtorHasAJob(dto.getTheFactThatTheDebtorHasAJob())
                .dateOfCourtDecision(dto.getDateOfCourtDecision())
                .dateUFSSP(dto.getDateUFSSP())
                .dateUVPSV(dto.getDateUVPSV())
                .legalDepartment(legalDepartment)
                .build();
    }

}
