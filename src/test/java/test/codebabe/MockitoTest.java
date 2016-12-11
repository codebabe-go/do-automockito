package test.codebabe;

import com.alibaba.fastjson.JSON;
import com.codebabe.util.ClassUtils;
import com.codebabe.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import test.codebabe.api.UserService;
import test.codebabe.model.User;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

/**
 * author: code.babe
 * date: 2016-12-04 13:52
 * mockito API测试
 */
public class MockitoTest {

    private UserService userService = new UserService();
    private User user;

    @Before
    public void before() {
        user = mock(User.class);

        userService.setUser(user);
    }

    @Test
    public void test() {
        // 具有传递性
        User u = user.test("fz");
        when(u).thenReturn(new User(1L, "fz", 20,"location"));
        System.out.println(JSON.toJSONString(userService.get("fz")));
    }

    /**
     * 在反射中的mockito, 不依赖于上述的条件
     */
    @Test
    public void testReflectMockito() throws InvocationTargetException, IllegalAccessException {
        Class<UserService> clz = UserService.class;

        UserService userService = ClassUtils.newInstance(clz);

        for (Method method : clz.getMethods()) {
            if (StringUtils.equals(method.getName(), "setUser")) {
                // Mock 这个
                method.invoke(userService, mock(User.class));
                break;
            }
        }
        User user = userService.user.test("fz");
        when(user).thenReturn(new User(1L, "fz", 20,"location"));
        for (Method method : clz.getMethods()) {
            if (StringUtils.equals(method.getName(), "get")) {
                System.out.println(JSON.toJSONString(method.invoke(userService, "fz")));
                break;
            }
        }
    }

}
