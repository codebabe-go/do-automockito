package com.codebabe.util;

/**
 * author: code.babe
 * date: 2016-12-01 14:01
 */
public class StringUtils extends org.apache.commons.lang.StringUtils {
    public final static String GETTER = "get";
    public final static String SETTER = "set";

    public static String fieldByMethod(String str, String prefix) {
        String fieldUpper = substringAfter(str, prefix);
        return reverseCaseByIndex(fieldUpper, 0);
    }

    public static String reverseCaseByIndex(String str, int index) {
        char[] ch = str.toCharArray();
        if (ch[index] >= 'A' && ch[index] <= 'Z') {
            ch[index] += 'a' - 'A';
            return new String(ch);
        }
        if (ch[index] >= 'a' && ch[index] <= 'z') {
            ch[index] -= 'a' - 'A';
            return new String(ch);
        }
        return str;
    }

    public static String trimBesideFigure(String str, String regex) {
        String first = substringAfterFirst(str, regex);
        String result = substringBeforeLast(first, regex);
        return result;
    }

    public static String substringAfterFirst(String str, String separator) {
        if (isEmpty(str)) {
            return str;
        }
        if (separator == null) {
            return EMPTY;
        }
        int pos = str.indexOf(separator);
        if (pos == -1) {
            return str;
        }
        return str.substring(pos + separator.length());
    }
}

