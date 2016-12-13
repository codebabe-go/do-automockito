package com.codebabe.parse;

import java.util.Map;

/**
 * author: code.babe
 * date: 2016-12-01 21:44
 * 开苞, 解开未解之谜, 不需要传入参数哦, 只要维护一个map就OK了
 */
public interface Unflowerred {

    /**
     * 去解开未解之谜吧
     * @param clz 需要测试的类
     * @param annotationClz 配合使用的注解
     * @param methodName 测试的方法
     * @param pathMap key: callable_method, value: returnTypePath
     * @param <T> 泛型类
     * @throws Exception 不规范异常, 这里没有做异常体系
     */
    <T> void go4Unflowerring(Class<T> clz, Class annotationClz, String methodName, Map<String, String> pathMap) throws Exception;

}
