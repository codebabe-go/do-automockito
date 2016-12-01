package com.codebabe.util;

import java.lang.reflect.Method;

/**
 * author: code.babe
 * date: 2016-12-01 16:36
 */
public class ClassUtils {

    public static <T> T assignValue(String fieldName, String value, T instance) {
        try {
            Class<T> clz = (Class<T>) instance.getClass();
            Class returnType = null;
            // 获取返回类型
            for (Method method : clz.getMethods()) {
                if (StringUtils.equals(method.getName(), StringUtils.GETTER + StringUtils.reverseCaseByIndex(fieldName, 0))) {
                    returnType = method.getReturnType();
                    break;
                }
            }
            for (Method method : clz.getMethods()) {
                if (StringUtils.equals(method.getName(), StringUtils.SETTER +  StringUtils.reverseCaseByIndex(fieldName, 0))) {
                    Object param = null;
                    if (returnType != null) {
                        if (!returnType.equals(String.class)) {
                            for (Method typeMethod : returnType.getMethods()) {
                                if ((StringUtils.startsWith(typeMethod.getName(), "parse") || StringUtils.startsWith(typeMethod.getName(), "valueOf")) && typeMethod.getParameterCount() == 1) {
                                    param = typeMethod.invoke(null, value);
                                }
                            }
                        } else {
                            param = value;
                        }
                    }
                    method.invoke(instance, param);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return instance;
    }

}
