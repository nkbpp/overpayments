package ru.pfr.overpayments.model.ros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VozPereRosDto {

    private String id;

    private Integer god;

    private Integer mes;

    private Integer nn;

    private Integer recId;

    private String doc;

    private Double s; //сумма возврата переплаты

    private LocalDate ppdate;

    private String ppnum;

}
