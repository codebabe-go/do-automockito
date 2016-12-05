package test.codebabe.api;

import test.codebabe.model.User;

/**
 * author: code.babe
 * date: 2016-12-04 14:04
 */
public class UserService {

    public User user;

    public User getUser(String param) {
        return user.test(param);
    }

    public void setUser(User user) {
        this.user = user;
    }
}
