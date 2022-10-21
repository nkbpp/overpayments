package ru.pfr.overpayments.model.annotations.snils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckSNILSValidator implements ConstraintValidator<CheckSNILS, String> {

    @Override
    public boolean isValid(String strSNILS, ConstraintValidatorContext context) {

        if(strSNILS == null){
            return false;
        }

        String numberOnly= strSNILS.replaceAll("[^0-9]", "");

        Integer controlSumm;
        String num;
        try{
            controlSumm = Integer.valueOf(strSNILS.substring(strSNILS.length()-2));
            num = strSNILS.substring(0,strSNILS.length()-2).replaceAll("[^0-9]", "");
        } catch (Exception e){
            return false;
        }

        if(Integer.valueOf(num) <= 1001998){
            return false;
        }

        int checkNum = 0;
        for (int i = 0,j = 9; i < num.length(); i++, j--) {
            checkNum += (Integer.valueOf(num.charAt(i)-'0') * j);
        }
        if(checkNum>101){
            checkNum%=101;
        }
        if(checkNum == 100 || checkNum == 101){
            checkNum = 00;
        }

        if(checkNum != controlSumm){
            return false;
        }

        return  true;
    }
}
