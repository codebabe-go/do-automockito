package com.codebabe;

import com.alibaba.fastjson.JSON;
import com.codebabe.model.MockCallModel;
import com.codebabe.model.PrintType;
import com.codebabe.parse.OpenIt;
import com.codebabe.util.StringUtils;
import org.apache.log4j.Logger;

import java.lang.reflect.Method;
import java.util.Map;

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
    protected <T> void mockData(MockCallModel mockCallModel, T instance, Map<String, Class> classMap) {
        // TODO: 02/12/2016 实现对 测试类的 when-mock 操作
        logger.debug(String.format("model info = %s", JSON.toJSONString(mockCallModel)));

        // 如果为空表示没有和上面的mock数据耦合, 可以直接操作
        if (StringUtils.isBlank(mockCallModel.getDetail())) {
            String fieldName = mockCallModel.getCallable();
            String methodName = mockCallModel.getMethod();
            Class callableClz = classMap.get(fieldName);
            if (callableClz != null) {
                Method[] methods = callableClz.getMethods();
                for (Method method : methods) {
                    if (StringUtils.equals(methodName, method.getName())) {
                        try {
                            execute(method, instance);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else {
            for (Map.Entry<String, Class> entry : classMap.entrySet()) {
                String fieldName = entry.getKey();
                Class clz = entry.getValue();

            }
        }
    }
}