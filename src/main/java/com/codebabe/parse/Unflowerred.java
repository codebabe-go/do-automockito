package com.codebabe.parse;

import java.lang.reflect.InvocationTargetException;

/**
 * author: code.babe
 * date: 2016-12-01 21:44
 * 开苞, 解开未解之谜
 */
public interface Unflowerred {

    /**
     * 去解开未解之谜吧
     * @param clz
     * @param annotationClz
     * @param methodName
     * @param args
     * @param <T>
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    <T> void go4Unflowerring(Class<T> clz, Class annotationClz, String methodName, Object... args) throws Exception;

}
