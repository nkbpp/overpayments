package ru.pfr.overpayments.model.overpayment.mapper.referenceBook;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.DocumentsDto;
import ru.pfr.overpayments.model.overpayment.dto.referenceBook.SpecificationOfTheReasonsForOverpaymentsDto;
import ru.pfr.overpayments.model.overpayment.entity.referenceBook.SpecificationOfTheReasonsForOverpayments;
import ru.pfr.overpayments.model.overpayment.mapper.DocumentsMapper;
import ru.pfr.overpayments.service.overpayment.DocumentsService;
import ru.pfr.overpayments.service.overpayment.referenceBook.ReasonsForOverpaymentsService;


@Component
@RequiredArgsConstructor
public class SpecificationOfTheReasonsForOverpaymentsMapper {

    private final ReasonsForOverpaymentsService reasonsForOverpaymentsService;
    private final DocumentsMapper documentsMapper;
    private final DocumentsService documentsService;

    public SpecificationOfTheReasonsForOverpaymentsDto toDto(SpecificationOfTheReasonsForOverpayments obj) {
        return SpecificationOfTheReasonsForOverpaymentsDto.builder()
                .id(obj.getId())
                .idReasonsForOverpayment(obj.getReasonsForOverpayments().getId())
                .specificationOfTheReasonsForOverpayments(obj.getSpecificationOfTheReasonsForOverpayments())
                .documentPensioner(
                        obj.getDocumentPensioner() == null ?
                                new DocumentsDto() :
                                documentsMapper.toDto(obj.getDocumentPensioner())
                )
                .documentCarer(
                        obj.getDocumentCarer() == null ?
                                new DocumentsDto() :
                                documentsMapper.toDto(obj.getDocumentCarer())
                )
                .build();
    }

    public SpecificationOfTheReasonsForOverpayments fromDto(SpecificationOfTheReasonsForOverpaymentsDto dto) {
        return SpecificationOfTheReasonsForOverpayments.builder()
                .id(dto.getId())
                .reasonsForOverpayments(
                        reasonsForOverpaymentsService.findById(dto.getIdReasonsForOverpayment())
                )
                .specificationOfTheReasonsForOverpayments(dto.getSpecificationOfTheReasonsForOverpayments())
                .documentPensioner(
                        dto.getDocumentPensioner().getId() == null ?
                                null :
                                documentsService.findById(dto.getDocumentPensioner().getId())
                )
                .documentCarer(
                        dto.getDocumentCarer().getId() == null ?
                                null :
                                documentsService.findById(dto.getDocumentCarer().getId())
                )
                .build();
    }

}
