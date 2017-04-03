package com.example.ecnill.postviewer.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ecnill on 14.3.17.
 */

public abstract class StringUtils {

    public static String replaceLastChar(String source, int addedToEnd) {
        return replaceLastChar(source, Integer.toString(addedToEnd));
    }

    public static String replaceLastChar(String source, String addedToEnd) {
        return source.substring(0, source.length() - 1) + addedToEnd;
    }

    public static String getActualDateTime() {
        Date date = new Date();
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(date);
    }

}
