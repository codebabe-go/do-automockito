package com.codebabe.parse;

import java.util.List;

/**
 * author: code.babe
 * date: 2016-11-29 19:35
 * 数据模板解析工具
 */
public interface Parser<T> {
    /**
     * 解析数据文件, 将这个数据文件转化为一个list
     * @param path
     * @return
     */
    List<T> parseData(String path);
}
