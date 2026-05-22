package org.example;

public class PasswordValidator {

    private static final int    MIN_LENGTH    = 8;
    private static final String SPECIAL_CHARS = "!@#$%";

    public static final String MSG_NULL     = "Password must not be null";
    public static final String MSG_LENGTH   = "Password must contain at least 8 characters";
    public static final String MSG_LOWER    = "Password must contain at least one lowercase letter";
    public static final String MSG_UPPER    = "Password must contain at least one uppercase letter";
    public static final String MSG_DIGIT    = "Password must contain at least one digit";
    public static final String MSG_SPECIAL  = "Password must contain at least one special character";
    public static final String MSG_VALID    = "Password is valid";

    public boolean isValid(String password) {
        return MSG_VALID.equals(getErrorMessage(password));
    }

    public String getErrorMessage(String password) {
        if (password == null)                    return MSG_NULL;
        if (password.length() < MIN_LENGTH)      return MSG_LENGTH;
        if (!containsLowercase(password))        return MSG_LOWER;
        if (!containsUppercase(password))        return MSG_UPPER;
        if (!containsDigit(password))            return MSG_DIGIT;
        if (!containsSpecialChar(password))      return MSG_SPECIAL;
        return MSG_VALID;
    }

    private boolean containsLowercase(String p) {
        for (char c : p.toCharArray()) if (Character.isLowerCase(c)) return true;
        return false;
    }

    private boolean containsUppercase(String p) {
        for (char c : p.toCharArray()) if (Character.isUpperCase(c)) return true;
        return false;
    }

    private boolean containsDigit(String p) {
        for (char c : p.toCharArray()) if (Character.isDigit(c)) return true;
        return false;
    }

    private boolean containsSpecialChar(String p) {
        for (char c : p.toCharArray()) if (SPECIAL_CHARS.indexOf(c) >= 0) return true;
        return false;
    }
}