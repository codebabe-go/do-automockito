package com.codebabe.common;

import com.codebabe.model.MockCallModel;
import com.codebabe.util.ClassUtils;
import com.codebabe.util.StringUtils;
import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: code.babe
 * date: 2016-12-01 21:17
 * 去扫描哪些mock出来类去调用了方法, 找出来集体去mock出数据
 * 这里只针对一般的情况, 也就是<p>varType result = dao.insert(obj);</p>
 */
public class MockCallScanner implements Scanner {

    private final static Logger logger = Logger.getLogger(MockCallScanner.class);

    public final static String ANNOTATION_NAME = "@MockCall";

    @Deprecated
    public Map<String, List<String>> scan4MockCall_deprecated(String path) throws IOException {
        Map<String, List<String>> map = new HashMap<>();
        File file = new File(path);
        if (file != null && file.exists() && file.isFile()) {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                // 如果满足条件就自动去获取下一行
                if (StringUtils.contains(line.trim(), ANNOTATION_NAME)) {
                    line = reader.readLine().trim();
                    String call = StringUtils.substringAfter(line, "=").trim();
                    String mockClass = StringUtils.substringBefore(call, ".");
                    // 去掉最后的分号
                    String _functionName = StringUtils.substring(call, mockClass.length() + 1, call.length() - 1);
                    String functionName = StringUtils.substringBefore(_functionName, "(");
                    if (map.containsKey(mockClass)) {
                        map.get(mockClass).add(functionName);
                    } else {
                        List<String> functions = new ArrayList<>();
                        functions.add(functionName);
                        map.put(mockClass, functions);
                    }
                }
            }

        }
        return map;
    }

    public List<MockCallModel> scan4MockCall(String path) throws IOException, ClassNotFoundException {
        List<MockCallModel> returns = new ArrayList<>();
        File file = new File(path);
        if (file != null && file.exists() && file.isFile()) {
            Map<String, Class> map = MockCallScanner.classMapping(file);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = reader.readLine()) != null) {
                // 如果满足条件就自动去获取下一行
                if (StringUtils.contains(line.trim(), ANNOTATION_NAME)) {
                    MockCallModel model = new MockCallModel();
                    String detail = StringUtils.substringBetween(line, "\"");
                    model.setDetail(detail);
                    line = reader.readLine().trim();
                    String call = StringUtils.substringAfter(line, "=").trim();
                    String mockClass = StringUtils.substringBefore(call, ".");
                    // 去掉最后的分号
                    String _functionName = StringUtils.substring(call, mockClass.length() + 1, call.length() - 1);
                    String functionName = StringUtils.substringBefore(_functionName, "(");
                    model.setCallable(mockClass);
                    model.setMethod(functionName);

                    String prefix = StringUtils.substringBefore(line, " ");
                    String returnType = "";
                    if (prefix.contains("<")) {
                        returnType = StringUtils.substringBetween(prefix, "<", ">");
                    } else {
                        returnType = prefix;
                    }
                    if (map.containsKey(returnType)) {
                        model.setReturnType(map.get(returnType));
                    } else {
                        logger.error(String.format("return type = %s, cannot match in map", returnType));
                    }
                    returns.add(model);
                }
            }
        }
        return returns;
    }

    public static Map<String, Class> classMapping(File file) throws IOException, ClassNotFoundException {
        Map<String, Class> map = new HashMap<>();
        BufferedReader reader = new BufferedReader(new FileReader(file));
        String line = null;
        while ((line = reader.readLine()) != null) {
            if (StringUtils.contains(line, "import") && !StringUtils.contains(line, "*")) {
                String packageName = StringUtils.substringBetween(line, " ", ";");
                map.put(StringUtils.substringAfterLast(packageName, "."), ClassUtils.loadClassByName(packageName));
            }
        }
        return map;
    }
}
