package com.codebabe.parse;

import com.codebabe.common.MockCallScanner;
import com.codebabe.util.MockUtils;
import com.codebabe.util.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * author: code.babe
 * date: 2016-12-01 21:46
 */
public class OpenIt implements Unflowerred {
    @Override
    public <T> void go4Unflowerring(Class<T> clz, Class annotationClz, String methodName, Object... args) throws Exception {
        if (clz == null) {
            return;
        }

        Field[] fields = clz.getFields();
        T instance = clz.newInstance();
        // 所有的注入filed都mock完毕
        for (Field field : fields) {
            Annotation annotation = field.getAnnotation(annotationClz);
            if (annotation != null) {
                String fieldName = field.getName();
                MockUtils.mockAndSet(fieldName, instance, field.getType());
            }
        }

        MockCallScanner scanner = new MockCallScanner();
        String path = clz.getResource("").getPath();
        Map<String, List<String>> map = scanner.scan4MockCall(path);

        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String mocked = entry.getKey();
            List<String> methods = entry.getValue();
            // TODO: 01/12/2016 需要每个field的字节码去做mock操作
//            when().thenReturn();
        }

        // 不专门去获取, 参数多少不是很好匹配
        for (Method method : clz.getMethods()) {
            if (StringUtils.equals(methodName, method.getName())) {
                method.invoke(instance, args);
                break;
            }
        }
    }
}
