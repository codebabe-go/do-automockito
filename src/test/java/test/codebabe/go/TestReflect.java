package test.codebabe.go;

import com.alibaba.fastjson.JSON;
import com.codebabe.util.ClassUtils;
import org.junit.Test;
import test.codebabe.model.User;

import java.lang.reflect.InvocationTargetException;

/**
 * author: code.babe
 * date: 2016-12-01 14:50
 */
public class TestReflect {

    @Test
    public void testAssign() throws IllegalAccessException, InstantiationException, InvocationTargetException {
        User user =  ClassUtils.assignValue("id", "11", new User());
        System.out.println(JSON.toJSONString(user));
    }

    @Test
    public void testReflect() {
        System.out.println(ClassUtils.newInstance(Long.class));
    }

}
