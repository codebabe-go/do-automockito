package com.codebabe.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * author: code.babe
 * date: 2016-12-01 16:36
 */
public class ClassUtils {

    // 对所有的instance做缓存, 不需要每次都去new一个出来
    private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE = new ConcurrentHashMap<Class<?>, Constructor<?>>() {{
        try {
            put(Long.class, Long.class.getConstructor(String.class));
            put(Integer.class, Integer.class.getConstructor(String.class));
            put(Double.class, Double.class.getConstructor(String.class));
            put(Timestamp.class, Timestamp.class.getConstructor(long.class));
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }};

    public static final Class[] EMPTY_ARRAY = new Class[]{};

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
                            for (Method typeMethod : returnType.getMethods()) { // 现在使用try-catch方式来解析, 可优化
                                try {
                                    if ((StringUtils.startsWith(typeMethod.getName(), "parse") && typeMethod.getParameterCount() == 1)) {
                                        param = typeMethod.invoke(null, value);
                                        break;
                                    }
                                    if (StringUtils.startsWith(typeMethod.getName(), "valueOf") && typeMethod.getParameterCount() == 1) {
                                        param = typeMethod.invoke(null, value);
                                        break;
                                    }
                                } catch (IllegalArgumentException e) {
                                    if ((StringUtils.startsWith(typeMethod.getName(), "parse") && typeMethod.getParameterCount() == 1)) {
                                        param = typeMethod.invoke(null, value);
                                        break;
                                    }
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

    /**
     * 只对部分参数进行实例化的支持
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> clazz) {
        T t = null;
        Constructor<T> constructor = (Constructor<T>) CONSTRUCTOR_CACHE.get(clazz);
        if(constructor == null){
            try {
                constructor = clazz.getDeclaredConstructor(EMPTY_ARRAY);
                constructor.setAccessible(true);
                CONSTRUCTOR_CACHE.put(clazz,constructor);
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
        try {
            if (Long.class.equals(clazz)) {
                t = constructor.newInstance("0");
            } else if (Integer.class.equals(clazz)) {
                t = constructor.newInstance("0");
            } else if (Double.class.equals(clazz)) {
                t = constructor.newInstance("0");
            } else if (Timestamp.class.equals(clazz)) {
                t = constructor.newInstance(0L);
            } else {
                t = constructor.newInstance(EMPTY_ARRAY);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return t;
    }

    public static Class loadClassByName(String completedName) throws ClassNotFoundException {
        return Thread.currentThread().getContextClassLoader().loadClass(completedName);
    }

}
