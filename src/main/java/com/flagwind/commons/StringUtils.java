package com.flagwind.commons;


public class StringUtils extends org.apache.commons.lang3.StringUtils {

    public static char[] CHARS = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E',
            'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

    public static String trim(String text, String... chars) {
        for (String c : chars) {
            text.replaceAll("^" + c + "+|" + c + "+$", "");
        }
        return text.trim();
    }

    public static boolean isPassword(String text) {
        return text.length() >= 6 && text.length() <= 16;
    }

    public static String generateRandom(int count) {
        String result = "";

        for (int i = 0; i < count; i++) {
            int id = new Double(Math.ceil(Math.random() * 35)).intValue();

            result += CHARS[id];
        }

        return result;
    }
}