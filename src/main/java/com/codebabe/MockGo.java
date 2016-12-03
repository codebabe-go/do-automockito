package com.codebabe;

import com.alibaba.fastjson.JSON;
import com.codebabe.model.MockCallModel;
import com.codebabe.model.PrintType;
import com.codebabe.parse.OpenIt;
import com.codebabe.util.ClassUtils;
import com.codebabe.util.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * author: code.babe
 * date: 2016-12-02 11:57
 * 主要提供mock数据的服务
 */
public class MockGo extends OpenIt {

    private final static Logger logger = Logger.getLogger(MockGo.class);

    public MockGo(PrintType printType) {
        super(printType);
    }

    @Override
    protected <T> void mockData(MockCallModel mockCallModel, T instance, Map<String, Class> classMap, Map<String, String> pathMap) {
        logger.debug(String.format("model info = %s", JSON.toJSONString(mockCallModel)));

        String fieldName = mockCallModel.getCallable();
        String methodName = mockCallModel.getMethod();

        // 如果为空表示没有和上面的mock数据耦合, 可以直接操作
        if (StringUtils.isBlank(mockCallModel.getDetail())) {
            Class callableClz = classMap.get(fieldName);
            if (callableClz != null) {
                Method[] methods = callableClz.getMethods();
                    for (Method method : methods) {
                    if (StringUtils.equals(methodName, method.getName())) {
                        try {
                            when(execute(method, instance)).thenReturn(mockReturnData(pathMap.get(methodName)));
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else { // 否则, 和之前的mock数据进行耦合
            Class clazz = classMap.get(fieldName);
            if (clazz == null) {
                return;
            }
            String mockcallAnno = mockCallModel.getDetail();
            // 参数-产生的类-产生的方法, 小嘴
            String[] detail = StringUtils.split(mockcallAnno, "-");
            String[] indexes = StringUtils.split(detail[0], "_");
            if (detail.length == 3) {
                if (StringUtils.equals(detail[1], methodName)) {
                    Class callableClz = classMap.get(fieldName);
                    if (callableClz != null) {
                        Method[] methods = callableClz.getMethods();
                        for (Method method : methods) {
                            if (StringUtils.equals(methodName, method.getName())) {
                                Class[] parameterType = method.getParameterTypes();
                                Object[] args = new Object[parameterType.length];
                                // 按顺序去遍历
                                for (int i = 0, j = 0; i < parameterType.length && j < indexes.length; i++) {
                                    if (StringUtils.equals(i + "", indexes[j])) {
                                        // TODO: 03/12/2016 上下相关的
                                        j++;
                                    } else {
                                        args[i] = ClassUtils.newInstance(parameterType[i]);
                                    }
                                }
                                try {
                                    when(method.invoke(indexes, args)).thenReturn(mockReturnData(pathMap.get(methodName)));
                                    break;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private Object mockReturnData(String path) {
        return null;
    }
}