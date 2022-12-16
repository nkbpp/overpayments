package ru.pfr.overpayments.model.dto;

import lombok.*;
import ru.pfr.overpayments.model.overpayment.dto.OverpaymentDto;
import ru.pfr.overpayments.model.ros.dto.ParentOverpaymentRosDto;
import ru.pfr.overpayments.model.ros.dto.VozPereRosDto;
import ru.pfr.overpayments.model.ros.dto.UderRosDto;
import ru.pfr.overpayments.model.ros.dto.VidVplRosDto;

import java.time.LocalDate;
import java.util.List;

//@Data //@ToString, @EqualsAndHashCode, @Getter, @Setter, @RequiredArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class FullOverpaymentDto extends ParentOverpaymentRosDto {

    private OverpaymentDto overpayment;

    @Builder
    public FullOverpaymentDto(Long isId, String id, String doc, Integer nn, LocalDate docdv, LocalDate sroks, LocalDate srokpo, LocalDate close_date, Double spe, Boolean vinap, VidVplRosDto vidVpl, List<UderRosDto> uderRosDto, List<VozPereRosDto> vozPereRosDto, OverpaymentDto overpayment) {
        super(isId, id, doc, nn, docdv, sroks, srokpo, close_date, spe, vinap, vidVpl, uderRosDto, vozPereRosDto);
        this.overpayment = overpayment;
    }
}
