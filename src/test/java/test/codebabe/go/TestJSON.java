package test.codebabe.go;

import com.alibaba.fastjson.JSON;
import org.junit.Test;
import test.codebabe.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * author: code.babe
 * date: 2016-11-30 19:10
 */
public class TestJSON {

    @Test
    public void test2JSON() {
        User user = new User(1L, "fz", 20, "netease");
        User user2 = new User(2L, "ll", 20, "netease");
        List<User> list = new ArrayList<User>();
        list.add(user);
        list.add(user2);
        System.out.println(JSON.toJSONString(list));
    }

}
