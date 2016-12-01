package com.codebabe.parse;

import java.io.IOException;
import java.util.List;

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
     * 将一行数据装成合适的json
     * @param line
     * @param regex
     * @param clazz
     * @return
     */
    T parseLine2Obj(String line, String regex, Class<T> clazz);
}
