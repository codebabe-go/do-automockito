package com.codebabe.parse;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

/**
 * author: code.babe
 * date: 2016-11-29 19:35
 * 数据模板解析工具
 */
public interface Parser<T> {
    /**
     * 解析数据文件, 将这个数据文件转化为一个list, 这里不接受目录
     * @param path
     * @return
     */
    List<T> parseData(String path, Class<T> clazz) throws IOException;

    /**
     * 将一行数据解析为一个实体类, 注意的是需要setter和getter方法
     * @param line
     * @param regex
     * @param clazz
     * @return
     */
    T parseLine2Obj(String line, String regex, Class<T> clazz, Map<String, Object> map) throws InvocationTargetException, IllegalAccessException;

    /**
     * 解析列名, 主要是每个field的名字
     * @param line
     * @param regex
     * @return k: name, v: index
     */
    Map<String, Object> parseHeader(String line, String regex);
}
