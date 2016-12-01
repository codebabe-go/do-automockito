package com.codebabe;

import org.apache.log4j.PropertyConfigurator;

/**
 * author: code.babe
 * date: 2016-11-29 19:26
 */
public class Main {

    public static void main(String[] args) {
        // log.properties位置
        String logPath = ClassLoader.getSystemResource("log4j.properties").getPath();
        PropertyConfigurator.configure(logPath);
    }

}
