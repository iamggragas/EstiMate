package com.example.main.Utilities;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    public boolean isValidPassword(String password) {
        if (password.length() < 8) { // checking password length
            return false;
        }

        // Define regex patterns
        Pattern upperCasePattern = Pattern.compile("[A-Z]");
        Pattern lowerCasePattern = Pattern.compile("[a-z]");
        Pattern digitPattern = Pattern.compile("\\d");
        Pattern specialCharPattern = Pattern.compile("[$@!%*?&]");

        // Check if password matches all the required patterns
        Matcher hasUpperCase = upperCasePattern.matcher(password);
        Matcher hasLowerCase = lowerCasePattern.matcher(password);
        Matcher hasDigit = digitPattern.matcher(password);
        Matcher hasSpecialChar = specialCharPattern.matcher(password);

        return hasUpperCase.find() && hasLowerCase.find() && hasDigit.find() && hasSpecialChar.find();
    }
}
