package ru.pfr.overpayments.model.ros.dto;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class OverpaymentRosDto extends ParentOverpaymentRosDto {
    @Builder
    public OverpaymentRosDto(Long isId, String id, String doc, LocalDate docdv, LocalDate sroks, LocalDate srokpo, LocalDate close_date, Double spe, Boolean vinap, VidVplRosDto vidVpl, List<UderRosDto> uderRosDto, List<VozPereRosDto> vozPereRosDto) {
        super(isId, id, doc, docdv, sroks, srokpo, close_date, spe, vinap, vidVpl, uderRosDto, vozPereRosDto);
    }
}
