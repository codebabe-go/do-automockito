package com.codebabe.model;

/**
 * author: code.babe
 * date: 2016-12-02 11:31
 * 注解@MockCall的实体类
 */
public class MockCallModel {

    public MockCallModel() {}

    public MockCallModel(String callable, String method, String detail) {
        this.callable = callable;
        this.method = method;
        this.detail = detail;
    }

    private String callable;
    private String method;
    private String detail;

    public String getCallable() {
        return callable;
    }

    public void setCallable(String callable) {
        this.callable = callable;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "MockCallModel{" +
                "callable='" + callable + '\'' +
                ", method='" + method + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
