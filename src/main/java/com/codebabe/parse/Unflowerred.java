package com.codebabe.parse;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * author: code.babe
 * date: 2016-12-01 21:44
 * 开苞, 解开未解之谜, 不需要传入参数哦, 只要维护一个map就OK了
 */
public interface Unflowerred {

    /**
     * 去解开未解之谜吧
     * @param clz
     * @param annotationClz
     * @param methodName
     * @param pathMap key: callable_method, value: returnTypePath
     * @param <T>
     * @throws IllegalAccessException
     * @throws InstantiationException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    <T> void go4Unflowerring(Class<T> clz, Class annotationClz, String methodName, Map<String, String> pathMap) throws Exception;

}
