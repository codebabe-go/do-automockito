package com.codebabe.parse;

import com.codebabe.anno.MockCall;
import com.codebabe.util.ClassUtils;
import com.codebabe.util.StringUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            // 获取第一行
            String line = reader.readLine();
            Map<String, Object> map = parseHeader(line, "\t");
            while ((line = reader.readLine()) != null) {
                T t = null;
                try {
                    t = parseLine2Obj(line, "\t", clazz, map);
                } catch (Exception e) {
                    logger.error(String.format("parse failed, line = %s", line), e);
                }
                if (t != null) {
                    list.add(t);
                }
            }
            return list;
        }
        return null;
    }

    // 不支持带有list的类, 可自行实现
    public T parseLine2Obj(String line, String regex, Class<T> clazz, Map<String, Object> map) throws InvocationTargetException, IllegalAccessException {
        String[] apart = line.split(regex);
        T instance = newInstance(clazz);
        if (instance != null) {
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                String filed = entry.getKey();
                Integer index = (Integer) entry.getValue();
                ClassUtils.assignValue(filed, StringUtils.trimBesideFigure(apart[index], "\""), instance);
            }
        }
        return instance;
    }

    public Map<String, Object> parseHeader(String line, String regex) {
        Map<String, Object> map = new HashMap<String, Object>();
        if (line != null) {
            String[] headers = line.split(regex);
            for (int i = 0; i < headers.length; i++) {
                map.put(headers[i], i);
            }
        }
        return map;
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
