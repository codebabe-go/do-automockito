package com.codebabe;

import com.codebabe.model.MockCallModel;
import com.codebabe.model.PrintType;
import com.codebabe.parse.OpenIt;

import java.util.Map;

/**
 * author: code.babe
 * date: 2016-12-02 11:57
 * 主要提供mock数据的服务
 */
public class MockGo extends OpenIt {
    public MockGo(PrintType printType) {
        super(printType);
    }

    @Override
    protected <T> void mockData(MockCallModel mockCallModel, T instance, Map<String, Class> classMap) {
        // TODO: 02/12/2016 实现对 测试类的 when-mock 操作
    }
}