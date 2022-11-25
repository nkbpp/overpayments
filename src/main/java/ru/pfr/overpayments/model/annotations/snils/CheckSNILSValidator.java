package ru.pfr.overpayments.model.annotations.snils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

public class CheckSNILSValidator implements ConstraintValidator<CheckSNILS, String> {

    @Override
    public boolean isValid(String strSNILS, ConstraintValidatorContext context) {

        if(strSNILS == null || strSNILS.equals("000-000-000 00")){
            throw new SNILSValidatorExeption("SNILS incorrect! SNILS is null!");
        }

        String regex="^\\d{3}-\\d{3}-\\d{3} \\d{2}$";
        if (!Pattern.matches(regex,strSNILS)) {
            throw new SNILSValidatorExeption("SNILS incorrect! SNILS " + strSNILS + " does not match the mask ddd-ddd-ddd dd");
        }

        int controlSumm = Integer.parseInt(strSNILS.substring(strSNILS.length() - 2));
        String num = strSNILS.substring(0,strSNILS.length()-2).replaceAll("\\D", "");

        //меньше 1001998
//        if(Integer.parseInt(num) <= 1001998){
//            throw new SNILSValidatorExeption("SNILS incorrect! Snils less than 1001998");
//        }

        int checkNum = 0;
        for (int i = 0,j = 9; i < num.length(); i++, j--) {
            checkNum += ((num.charAt(i) - '0') * j);
        }
        if(checkNum>101){
            checkNum%=101;
        }
        if(checkNum == 100 || checkNum == 101){
            checkNum = 0;
        }

        if (checkNum == controlSumm) {
            return true;
        } else {
            throw new SNILSValidatorExeption("SNILS incorrect! Check number " +
                    controlSumm + " is wrong! Correct is " + checkNum);
        }
        //return checkNum == controlSumm;
    }
}

