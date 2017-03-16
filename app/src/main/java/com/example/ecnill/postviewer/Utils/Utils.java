package com.example.ecnill.postviewer.Utils;

/**
 * Created by ecnill on 14.3.17.
 */

public class Utils {

    public static String replaceLastChar(String source, int addedToEnd) {
        return replaceLastChar(source, Integer.toString(addedToEnd));
    }

    public static String replaceLastChar(String source, String addedToEnd) {
        return source.substring(0, source.length() - 1) + addedToEnd;
    }

}
