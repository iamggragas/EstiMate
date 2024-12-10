package com.example.main.Utilities;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class EmailValidator {
    String EMAIL_REGEX = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
    Pattern pattern = Pattern.compile(EMAIL_REGEX);

    public boolean isValidEmail(String email) {
        if (email == null) {
            return false; // Null check
        }
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
