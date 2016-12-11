package com.codebabe;

import com.alibaba.fastjson.JSON;
import com.codebabe.model.Entity;
import com.codebabe.model.MockCallModel;
import com.codebabe.model.PrintType;
import com.codebabe.parse.OWLExportParser;
import com.codebabe.parse.OpenIt;
import com.codebabe.parse.Parser;
import com.codebabe.util.ClassUtils;
import com.codebabe.util.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
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
    protected <T> void mockData(MockCallModel mockCallModel, T instance, Map<String, Entity> entityMap, Map<String, String> pathMap) {
        logger.debug(String.format("model info = %s", JSON.toJSONString(mockCallModel)));

        String fieldName = mockCallModel.getCallable();
        String methodName = mockCallModel.getMethod();

        // 如果为空表示没有和上面的mock数据耦合, 可以直接操作
        if (StringUtils.isBlank(mockCallModel.getDetail())) {
            Class callableClz = entityMap.get(fieldName).getClz();
            if (callableClz != null) {
                Method[] methods = callableClz.getMethods();
                    for (Method method : methods) {
                    if (StringUtils.equals(methodName, method.getName())) {
                        try {
                            Object execution = execute(method, instance);
                            when(execution).thenReturn(mockReturnData(pathMap.get(methodName), execution));
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else { // 否则, 和之前的mock数据进行耦合
            Class clazz = entityMap.get(fieldName).getClz();
            if (clazz == null) {
                return;
            }
            String mockcallAnno = mockCallModel.getDetail();
            // 参数-产生的类-产生的方法, 小嘴
            String[] detail = StringUtils.split(mockcallAnno, "-");
            String[] indexes = StringUtils.split(detail[0], "_");
            if (detail.length == 3) {
                if (StringUtils.equals(detail[1], methodName)) {
                    Class callableClz = entityMap.get(fieldName).getClz();
                    if (callableClz != null) {
                        Method[] methods = callableClz.getMethods();
                        for (Method method : methods) {
                            if (StringUtils.equals(methodName, method.getName())) {
                                Class[] parameterType = method.getParameterTypes();
                                Object[] args = new Object[parameterType.length];
                                // 按顺序去遍历
                                for (int i = 0, j = 0; i < parameterType.length && j < indexes.length; i++) {
                                    // 如果是基于mock数据的需要拿到上面mock出来的值
                                    if (StringUtils.equals(i + "", indexes[j])) {
                                        // TODO: 03/12/2016 上下相关的
                                        j++;
                                    } else { // 否则直接给出默认值
                                        args[i] = ClassUtils.newInstance(parameterType[i]);
                                    }
                                }
                                try {
                                    Object execution = method.invoke(indexes, args);
                                    when(execution).thenReturn(mockReturnData(pathMap.get(methodName), execution));
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

    private Object mockReturnData(String path, Object instance) {
        Parser parser = new OWLExportParser();
        try {
            return parser.parseData(path, instance.getClass());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}