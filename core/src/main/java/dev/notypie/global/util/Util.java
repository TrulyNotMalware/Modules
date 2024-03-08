package dev.notypie.global.util;

import dev.notypie.global.error.exceptions.CommonErrorCodeImpl;
import dev.notypie.global.error.exceptions.CommonException;

import java.util.regex.Pattern;

public class Util {

    public static void validateString(String regex, String expression){
        Pattern passwordRegex = Pattern.compile(regex);
        if (!passwordRegex.matcher(expression).find()){
            System.out.println("Regex failed "+regex+", expression="+expression);
            throw CommonException.builder()
                    .errorCode(CommonErrorCodeImpl.REGEX_NOT_EXPECTED)
                    .build();
        }
    }
}
