package com.codebabe.util;

import java.util.ArrayList;
import java.util.List;

/**
 * author: code.babe
 * date: 2016-12-12 17:49
 * 专门解析mockCall注解的一个工具类
 */
public class ParseMockCall {

    private final static String FIRST_SMALL_SMILE = "-";
    private final static String SECOND_BIG_SMILE = "_";

    /**
     * 只存在两种情况, 一种是
     * @param mockCallDetail
     * @return
     */
    public static List<String[]> parse(String mockCallDetail) {
        List<String[]> list = new ArrayList<>();
        if (StringUtils.isEmpty(mockCallDetail)) {
            return list;
        }
        String[] splitArr = StringUtils.split(mockCallDetail, FIRST_SMALL_SMILE);
        if (splitArr.length < 3) {
            return list;
        }

        String[] indexOfParam = StringUtils.split(splitArr[0], SECOND_BIG_SMILE);
        String[] className = StringUtils.split(splitArr[1], SECOND_BIG_SMILE);
        String[] methodName = StringUtils.split(splitArr[2], SECOND_BIG_SMILE);

        // 这些长度必须要一致
        if (indexOfParam.length == className.length && className.length == methodName.length) {
            list.add(indexOfParam);
            list.add(className);
            list.add(methodName);
        }

        return list;
    }

}
