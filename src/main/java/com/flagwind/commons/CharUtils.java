package com.flagwind.commons;

public class CharUtils {


    public static boolean isLatin(char c) {

        return (int) c <= 255;
    }

    public static boolean isAscii(char c) {
        return (int) c <= 127;
    }

    public static boolean isLetterOrDigit(char c) {
        return Character.isLetterOrDigit(c);
    }

    public static boolean isWhiteSpace(char c) {

        int code = (int) c;

        return code == 32 || code >= 9 && code <= 13 || code == 160 || code == 133;
    }
}
