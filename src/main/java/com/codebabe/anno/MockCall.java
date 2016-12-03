package com.codebabe.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author: code.babe
 * date: 2016-12-01 20:47
 * 任何地方都能放使用的时候要特别注意, 标识一下哪些有返回值的方法需要去mock数据
 */
@Target(ElementType.TYPE_USE)
@Retention(RetentionPolicy.RUNTIME)
public @interface MockCall {
    /**
     * 参数位置-产生的类-产生的方法, 多个参数之间使用"_"来连接, 位置从0开始计数
     * @default 默认没有中途产生的变量调用
     * @return
     */
    String region() default "";
}
