package ru.pfr.overpayments.model.overpayment.mapper.log;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.pfr.overpayments.model.overpayment.dto.log.LogiDto;
import ru.pfr.overpayments.model.overpayment.entity.log.Logi;

@Component
@RequiredArgsConstructor
public class LogiMapper {

    public LogiDto toDto(Logi obj) {
        return LogiDto.builder()
                .id(obj.getId())
                .date(obj.getDate())
                .type(obj.getType())
                .text(obj.getText())
                .user(obj.getUser())
                .build();
    }

    public Logi fromDto(LogiDto dto) {
        return Logi.builder()
                .id(dto.getId())
                .date(dto.getDate())
                .type(dto.getType())
                .text(dto.getText())
                .user(dto.getUser())
                .build();
    }

}
