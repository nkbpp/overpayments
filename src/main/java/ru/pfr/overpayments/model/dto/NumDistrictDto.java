package ru.pfr.overpayments.model.dto;

import ru.pfr.overpayments.model.annotations.district.District;

public class NumDistrictDto {

    /****
     Белгороде 1
     Старый Осколе 2
     Губкине 3
     Шебекино 4
     Алексеевке 5
     Валуйки 6
     Белгородском районе 7
     Волоконовском районе 13
     Прохоровском районе 14
     Ивнянском районе 15
     Яковлевском районе 16
     Ракитянском районе 17
     Борисовском районе 18
     Красненском районе 19
     Корочанском районе 20
     Грайворонском районе 21
     Вейделевском районе 22
     Ровеньском районе 23
     Красногвардейском районе 24
     Новооскольском районе 25
     Чернянском районе 26
     Краснояружском районе 27
     * */

    @District
    private Integer numDistrict;

    public NumDistrictDto() {
    }

    public NumDistrictDto(Integer numDistrict) {
        this.numDistrict = numDistrict;
    }

    public Integer getNumDistrict() {
        return numDistrict;
    }

    public void setNumDistrict(Integer numDistrict) {
        this.numDistrict = numDistrict;
    }
}


