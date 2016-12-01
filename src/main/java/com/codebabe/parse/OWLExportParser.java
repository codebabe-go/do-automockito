package com.codebabe.parse;

import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author: code.babe
 * date: 2016-11-30 16:42
 * 面向网易的owl镜像库导出的模板数据
 */
public class OWLExportParser<T> implements Parser<T> {

    private final static Logger logger = Logger.getLogger(OWLExportParser.class);

    public List<T> parseData(String path, Class<T> clazz) throws IOException {
        File file = new File(path);
        if (file != null && file.exists() && file.isFile()) {
            List<T> list = new ArrayList<T>();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                T t = parseLine2Obj(line, "\t", clazz);
                if (t != null) {
                    list.add(t);
                }
            }
            return list;
        }
        return null;
    }

    // 不支持带有list的类, 可执行实现
    public T parseLine2Obj(String line, String regex, Class<T> clazz) {
        String[] apart = line.split(regex);
        T instance = newInstance(clazz);
        if (instance != null) {
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {

            }
        }
        return instance;
    }

    private T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException e) {
            logger.error(e);
        } catch (IllegalAccessException e) {
            logger.error(e);
        }
        return null;
    }
}
