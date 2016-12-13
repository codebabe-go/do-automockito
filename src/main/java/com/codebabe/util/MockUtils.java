package com.codebabe.util;

import com.codebabe.model.Entity;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * author: code.babe
 * date: 2016-12-01 20:24
 */
public class MockUtils {

    /**
     * 利用mockito建立mock数据和实体类关系
     * @param filed
     * @param instance
     * @param des
     */
    public static <T> void mockAndSet(String filed, T instance, Class des, Map<String, Entity> instanceMap) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Class<T> clz = (Class<T>) instance.getClass();
        Method method = clz.getMethod(StringUtils.SETTER + StringUtils.reverseCaseByIndex(filed, 0), des);
        Object fieldInstance = mock(des);
        instanceMap.put(filed, new Entity(filed, fieldInstance, des));
        method.invoke(instance, fieldInstance);
    }

}
