package ru.pfr.overpayments.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class StatisticsDto {

    //Остаток переплат на начало месяца
    private Integer revealed;//выявлено
    private Integer redeemed;//Погашено
    //Удержано
    //Списано
    //Переплата выбыла
    //Остаток на конец месяца


    public StatisticsDto() {
        revealed=0;
        redeemed=0;
    }
}
