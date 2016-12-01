package com.codebabe.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

/**
 * author: code.babe
 * date: 2016-12-01 20:24
 */
public class MockUtils {

    /**
     * 利用mockito框架设置
     * @param filed
     * @param instance
     * @param des
     */
    public static <T> void mockAndSet(String filed, T instance, Class des) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<T> clz = (Class<T>) instance.getClass();
        Method method = clz.getMethod(StringUtils.SETTER + StringUtils.reverseCaseByIndex(filed, 0), des);
        method.invoke(instance, mock(des));
    }

}
