package com.codebabe.model;

/**
 * author: code.babe
 * date: 2016-12-02 13:31
 */
public class PrintType {

    public interface Type {
        int ASSERT = 1;
        int S_OUT = 2;
    }

    private int type;
    private Object data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
