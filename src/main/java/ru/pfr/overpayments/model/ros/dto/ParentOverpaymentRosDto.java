package ru.pfr.overpayments.model.ros.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.pfr.overpayments.model.annotations.fio.CustomDateSerializerRu;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentOverpaymentRosDto {

    private Long isId;

    private String id;

    private String doc; //номер документа

    @JsonSerialize(using = CustomDateSerializerRu.class)
    private LocalDate docdv; //дата выдачи исполнит.док-та

    @JsonSerialize(using = CustomDateSerializerRu.class)
    private LocalDate sroks; //дата начала выплат

    @JsonSerialize(using = CustomDateSerializerRu.class)
    private LocalDate srokpo; //дата окончания удержания

    @JsonSerialize(using = CustomDateSerializerRu.class)
    private LocalDate close_date; //дата закрытия удержания

    private Double spe; //общая сумма

    private Boolean vinap; //Вина пенсионера

    private VidVplRosDto vidVpl;

    private List<UderRosDto> uderRosDto;

    private List<VozPereRosDto> vozPereRosDto;

}
