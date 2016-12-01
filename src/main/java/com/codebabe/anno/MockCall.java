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
}
