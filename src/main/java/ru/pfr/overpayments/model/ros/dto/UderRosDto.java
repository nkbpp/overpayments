package ru.pfr.overpayments.model.ros.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UderRosDto {

    private Long recId;

    private String idMan;

    private Integer god;

    private Integer mes;

    private Integer doc;

    private Double ub; //удержано из БЧ

    private Double us; //удержано из CЧ

    private Double uSddpm; //удержание

    private Double ouSddpm; //остаток удержания

    private Double uderPercent; //процент удержания с твердой суммы

}
