/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import dao.AccountDAO;
import dao.AccountDAOImpl;
import dao.CategoryDAO;
import dao.CategoryDAOImpl;
import dao.ProductDAO;
import dao.ProductDAOImpl;
import java.security.SecureRandom;
import java.util.regex.Pattern;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author Trung
 */
public class Santinization {

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom random = new SecureRandom();

    public static String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(index));
        }
        return sb.toString();
    }

    public static boolean checkStringContain(String[] txts, String txtToCompare) {
        for (int i = 0; i < txts.length; i++) {
            if (txtToCompare.contains(txts[i])) {
                return true;
            };
        }

        return false;
    }

    public static String capitalizeFirstLetters(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        StringBuilder result = new StringBuilder(input.length());
        boolean capitalizeNext = true;

        for (char c : input.toCharArray()) {
            if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                c = Character.toUpperCase(c);
                capitalizeNext = false;
            }
            result.append(c);
        }

        return result.toString();
    }

    public static int checkEmptyString(HttpServletRequest request, String input, String errorName, String message) {
        if (input == null || input.trim().isEmpty()) {
            request.setAttribute(errorName, message);
            return 1;
        }

        return 0;
    }

    public static int checkNoSpaceAndNoCapitalize(HttpServletRequest request, String input, String errorName, String message) {
        if (input == null) {
            return 0;
        }

        for (int i = 0; i < input.length(); i++) {
            if (Character.isWhitespace(input.charAt(i))) {
                request.setAttribute(errorName, message);
                return 1;
            }
        }

        for (int i = 0; i < input.length(); i++) {
            if (Character.isUpperCase(input.charAt(i))) {
                request.setAttribute(errorName, message);
                return 1;
            }
        }

        return 0;
    }

    public static int matchesRegex(HttpServletRequest request, String input, String regex, String errorName, String message) {
        if (input == null || regex == null) {
            throw new IllegalArgumentException("Input and regex must not be null");
        }

        if (!Pattern.matches(regex, input)) {
            request.setAttribute(errorName, message);
            return 1;
        };

        return 0;
    }

    public static int doubleToInt(double num) {
        return (int) num;
    }

    public static int parseInt(String num, int defaultNum) {
        try {
            return Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return defaultNum;
        }
    }
}
