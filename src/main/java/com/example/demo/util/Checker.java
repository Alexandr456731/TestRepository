package com.example.demo.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class Checker {
    public String checkPassword(String password) {
        char[] chars = password.toCharArray();

        if (password == null || password.isEmpty() || password.length() < 8) {
            return "Пароль слишком короткий!";
        }

        String[] mass = password.split(" ");
        if (mass.length > 1 || chars[0] == ' ' || chars[password.length() - 1] == ' ') {
            return "Пароль не может содержать пробелы!";
        }

        char[] rusChars = "ёйцукенгшщзхъфывапролджэячсмитьбю".toCharArray();
        for (char el : chars) {
            for (char rusEl : rusChars) {
                if (el == rusEl) {
                    return "Пароль не должен содержать киррилицу";
                }
            }
        }

        char[] numberChars = "0123456789".toCharArray();
        boolean isNumber = false;
        for (char el : chars) {
            for (char numberEl : numberChars) {
                if (el == numberEl) {
                    isNumber = true;
                    break;
                }
            }
        }

        char[] letterLowerChars = "qwertyuiopasdfghjklzxcvbnm".toCharArray();
        char[] letterUpperChars = "qwertyuiopasdfghjklzxcvbnm".toUpperCase().toCharArray();
        boolean isLowerLetter = false;
        boolean isUpperLetter = false;
        for (char el : password.toCharArray()) {
            for (char letterEl : letterLowerChars) {
                if (el == letterEl) {
                    isLowerLetter = true;
                    break;
                }
            }

            for (char letterEl : letterUpperChars) {
                if (el == letterEl) {
                    isUpperLetter = true;
                    break;
                }
            }
        }

        if (!(isLowerLetter && isUpperLetter && isNumber)) {
            return "Пароль слишеом простой!";
        }

        return null;
    }
}
