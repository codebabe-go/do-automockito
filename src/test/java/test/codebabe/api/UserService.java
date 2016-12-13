package test.codebabe.api;

import com.codebabe.anno.MockCall;
import test.codebabe.model.User;

import javax.annotation.Resource;

/**
 * author: code.babe
 * date: 2016-12-04 14:04
 */
public class UserService {

    @Resource
    public User user;

    public User get(String param) {
        @MockCall
        User ret = user.test(param);
        return ret;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
