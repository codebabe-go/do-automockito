package com.codebabe;

import com.alibaba.fastjson.JSON;
import com.codebabe.model.Entity;
import com.codebabe.model.MockCallModel;
import com.codebabe.model.PrintType;
import com.codebabe.parse.OWLExportParser;
import com.codebabe.parse.OpenIt;
import com.codebabe.parse.Parser;
import com.codebabe.util.ClassUtils;
import com.codebabe.util.ParseMockCall;
import com.codebabe.util.StringUtils;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;

/**
 * author: code.babe
 * date: 2016-12-02 11:57
 * 主要提供mock数据的服务
 */
public class MockGo extends OpenIt {

    private final static Logger logger = Logger.getLogger(MockGo.class);

    public MockGo(int type, Object data) {
        super(type, data);
    }

    public MockGo(int type) {
        super(type);
    }

    public MockGo(PrintType printType) {
        super(printType);
    }

    @Override
    protected void mockData(MockCallModel mockCallModel, Map<String, Entity> entityMap, Map<String, String> pathMap, Map<String, Object> resultMap) throws InvocationTargetException, IllegalAccessException {
        logger.debug(String.format("model info = %s", JSON.toJSONString(mockCallModel)));

        String fieldName = mockCallModel.getCallable();
        String methodName = mockCallModel.getMethod();

        // 如果为空表示没有和上面的mock数据耦合, 可以直接操作
        if (StringUtils.isBlank(mockCallModel.getDetail())) {
            Entity entity = entityMap.get(fieldName);
            if (entity != null) {
                Class callableClz = entity.getClz();
                Method[] methods = callableClz.getMethods();
                for (Method method : methods) {
                    if (StringUtils.equals(methodName, method.getName())) {
                        try {
                            Object result = mockReturnData(pathMap.get(methodName), mockCallModel.getReturnType());
                            when(execute(method, entity.getInstance())).thenReturn(result);
                            resultMap.put(methodName, result);
                            return;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } else { // 否则, 和之前的mock数据进行耦合

            Entity entity = entityMap.get(fieldName);
            if (entity == null) {
                logger.warn(String.format("[mockData]entity is null, field name = " + fieldName));
                return;
            }
            String detail = mockCallModel.getDetail();
            List<String[]> mockInfoDetail = ParseMockCall.parse(detail);
            if (mockInfoDetail.size() > 0) {
                // 和mock数据耦合的参数位置
                String[] indexOfParam = mockInfoDetail.get(0);
                // 返回值的那个字段
                String[] returnFields = mockInfoDetail.get(1);
                // 对应的这些类的方法
                String[] methodNames = mockInfoDetail.get(2);

                // 这些在数组中的位置都是对应的关系

                Object instance = entity.getInstance();
                Class instanceClz = instance.getClass();
                for (Method instanceMethod : instanceClz.getMethods()) {
                    if (StringUtils.equals(methodName, instanceMethod.getName())) {
                        Class[] parameterTypes = instanceMethod.getParameterTypes();
                        Object[] args = new Object[parameterTypes.length];
                        for (int i = 0, j = 0; i < parameterTypes.length && j < indexOfParam.length; i ++) { // 对相应的位置进行参数适配
                            if (StringUtils.equals(i + "", indexOfParam[j])) {
                                String connectedMockMethod = methodNames[i];
                                String connectedMockFiled = returnFields[i];
                                Object mockResult = resultMap.get(connectedMockMethod);
                                if (StringUtils.equals("self", connectedMockFiled)) {
                                    args[i] = mockResult;
                                } else {
                                    // 结果集如果是list, 需要特殊的处理
                                    if (mockResult instanceof List) {
                                        int index = Integer.parseInt(StringUtils.substringAfter(connectedMockFiled, ":"));
                                        List mockList = (List) mockResult;
//                                        for (Method method : mockList.get(index).getClass().getMethods()) {
//                                            if (StringUtils.equals(method.getName(), StringUtils.GETTER + StringUtils.reverseCaseByIndex(StringUtils.substringBefore(connectedMockFiled, ":"), 0))) {
//                                                // 通过get方法直接获取
//                                                args[i] = method.invoke(mockList.get(index));
//                                                break;
//                                            }
//                                        }
                                    } else {
                                        for (Method method : mockResult.getClass().getMethods()) {
                                            if (StringUtils.equals(method.getName(), StringUtils.GETTER + StringUtils.reverseCaseByIndex(connectedMockFiled, 0))) {
                                                // 通过get方法直接获取
                                                args[i] = method.invoke(mockResult);
                                                break;
                                            }
                                        }
                                    }
                                }
                                j++;
                            } else { // 否则直接给出默认值
                                // 目前只支持 基本类型和Timestamp类
                                args[i] = ClassUtils.newInstance(parameterTypes[i]);
                            }
                        }
                        try {
                            Object result = mockReturnData(pathMap.get(methodName), mockCallModel.getReturnType());
                            when(instanceMethod.invoke(instance, args)).thenReturn(result);
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error(e);
                        } finally {
                            break;
                        }
                    }
                }
            }
        }
    }

    private Object mockReturnData(String path, Class clz) {
        Parser parser = new OWLExportParser();
        try {
            Object result = parser.parseData(path, clz);
            if (result instanceof List) { // 返回值都是list
                List ret = (List) result;
                if (ret.size() == 0) {
                    return null;
                } else if(ret.size() == 1) {
                    return ret.get(0);
                } else {
                    return ret;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}