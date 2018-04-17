package com.qi.chat.common.packages;

/**
 * Created by qi on 2018/4/16.
 */
public class TextUtil {

    public static boolean isEmptyOrNull(String text) {
        return null == text || text.equals("");
    }

    public static String getNotNullString(String text) {
        return isEmptyOrNull(text) ? "" : text;
    }

}
