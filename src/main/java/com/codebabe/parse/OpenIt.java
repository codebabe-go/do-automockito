package com.codebabe.parse;

import com.codebabe.common.MockCallScanner;
import com.codebabe.model.Entity;
import com.codebabe.model.MockCallModel;
import com.codebabe.model.PrintType;
import com.codebabe.util.ClassUtils;
import com.codebabe.util.MockUtils;
import com.codebabe.util.StringUtils;
import org.junit.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: code.babe
 * date: 2016-12-01 21:46
 */
public abstract class OpenIt implements Unflowerred {

    public OpenIt(PrintType printType) {
        this.printType = printType;
    }

    private PrintType printType;

    @Override
    public <T> void go4Unflowerring(Class<T> clz, Class annotationClz, String methodName, Map<String, String> pathMap) throws Exception {
        if (clz == null) {
            return;
        }

        if (!(printType.getType() == PrintType.Type.S_OUT || printType.getType() == PrintType.Type.ASSERT)) {
            throw new Exception(String.format("[go4Unflowerring]No print type = %d match", printType.getType()));
        }

        Field[] fields = clz.getFields();
        T instance = clz.newInstance();
        Map<String, Entity> classMap = new HashMap<>();
        // 所有的注入filed都mock完毕
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(annotationClz);
            if (annotation != null) {
                String fieldName = field.getName();
                MockUtils.mockAndSet(fieldName, instance, field.getType(), classMap);
            }
        }

        MockCallScanner scanner = new MockCallScanner();
        String path = clz.getResource("").getPath();
        List<MockCallModel> mockitoList = scanner.scan4MockCall(path);

        for (MockCallModel mockCallModel : mockitoList) {
            mockData(mockCallModel, classMap, pathMap);
        }

        if (printType.getType() == PrintType.Type.S_OUT) {
            for (Method method : clz.getMethods()) {
                if (StringUtils.equals(methodName, method.getName())) {
                    System.out.println(execute(method, instance));
                    break;
                }
            }
        }
        if (printType.getType() == PrintType.Type.ASSERT) {
            for (Method method : clz.getMethods()) {
                if (StringUtils.equals(methodName, method.getName())) {
                    Assert.assertTrue(printType.getData().equals(execute(method, instance)));
                    break;
                }
            }
        }
    }

    public PrintType getPrintType() {
        return printType;
    }

    public void setPrintType(PrintType printType) {
        this.printType = printType;
    }

    /**
     * 特有的执行方法, mock数据的时候默认传入的参数, number为0, varchar为""
     * @param method
     * @param instance
     * @param <T>
     * @return
     * @throws InvocationTargetException
     * @throws IllegalAccessException
     */
    protected  <T> Object execute(Method method, T instance) throws InvocationTargetException, IllegalAccessException {
        Class[] parameterType = method.getParameterTypes();
        Object[] args = new Object[parameterType.length];
        for (int i = 0; i < parameterType.length; i++) {
            args[i] = ClassUtils.newInstance(parameterType[i]);
        }
        return method.invoke(instance, args);
    }

    /**
     * 调用mockito的when-return方法对数据进行mock
     *
     * @param mockCallModel 每个调用MockCall注解的 方法信息
     * @param classMap 已经mock过的类组成的map, <p>k: fieldName, v: fieldInstance</p>
     * @param <T> 实例的泛型
     */
    protected abstract <T> void mockData(MockCallModel mockCallModel, Map<String, Entity> classMap, Map<String, String> pathMap);
}
