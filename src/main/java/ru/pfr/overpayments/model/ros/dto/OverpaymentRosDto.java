package ru.pfr.overpayments.model.ros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OverpaymentRosDto {

    private Long isId;

    private String id;

    private String doc; //номер документа

    private LocalDate docdv; //дата выдачи исполнит.док-та

    private LocalDate sroks; //дата начала выплат

    private LocalDate srokpo; //дата окончания удержания

    private LocalDate close_date; //дата закрытия удержания

    private Double spe; //общая сумма

    private Boolean vinap; //Вина пенсионера

    private VidVplRosDto vidVpl;

    private List<UderRosDto> uderRosDto;

    private List<VozPereRosDto> vozPereRosDto;

}
