package com.codebabe.model;

/**
 * author: code.babe
 * date: 2016-12-11 22:46
 * 映射类
 */
public class Entity {

    public Entity() {
    }

    public Entity(String name, Object instance, Class clz) {
        this.name = name;
        this.instance = instance;
        this.clz = clz;
    }

    private String name;
    private Object instance;
    private Class clz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Class getClz() {
        return clz;
    }

    public void setClz(Class clz) {
        this.clz = clz;
    }
}
