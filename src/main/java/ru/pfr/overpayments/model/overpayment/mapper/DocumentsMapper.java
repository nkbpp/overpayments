package ru.pfr.overpayments.model.overpayment.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.DocumentsDto;
import ru.pfr.overpayments.model.overpayment.entity.Documents;

@RequiredArgsConstructor
@Component
public class DocumentsMapper {

    public DocumentsDto toDto(Documents obj) {
        return DocumentsDto.builder()
                .id(obj.getId())
                .nameFile(obj.getNameFile())
                .build();
    }

    public Documents fromDto(DocumentsDto dto) {
        return Documents.builder()
                .id(dto.getId())
                .nameFile(dto.getNameFile())
                .build();
    }

}
