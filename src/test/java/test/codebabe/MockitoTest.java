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
import java.util.HashMap;
import java.util.Map;

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

    /**
     * 通过getter方法来建立连接
     */
    @Test
    public void testGetter() throws InvocationTargetException, IllegalAccessException, InstantiationException {
        // 1.创建一个UserService
        // 2.利用反射mock User, 然后塞到UserService中去
        // 3.直接调用那个test方法

        // 当前名字和类型的映射
        Map<String, Class> mapper = new HashMap<>();

        Class<UserService> userServiceClass = UserService.class;
        UserService userService = ClassUtils.newInstance(userServiceClass);

        // 用例为了保证时序 使用两次循环来进行操作
        for (Method method : userServiceClass.getMethods()) {
            if (StringUtils.equals(method.getName(), "setUser")) {
                User user = mock(User.class);
                method.invoke(userService, user);
                // 这里有两种方式建立关系
                // 1). 直接将这个user.getClass()塞进去
                mapper.put("user", user.getClass());
                // 2). 从getUser()里面拿
            }
        }

        String param = "fz";

        for (Method method : userServiceClass.getMethods()) {
            if (StringUtils.equals(method.getName(), "get")) {
                // 实际上是取获得user的mock数据
                Class userClass = mapper.get("user");
                for (Method inner : userClass.getMethods()) {
                    if (inner.getName().equals("test")) {
                        // 这里需要操作一个实例, 而不是一个已经建立好关系的class流
//                        User user = (User) mapper.get("user").newInstance();
                        for (Method getter : userServiceClass.getMethods()) {
                            if (getter.getName().equals("getUser")) {
                                when(inner.invoke(/*invoke对象就是get出来的*/ getter.invoke(userService), param)).thenReturn(new User(1L, "fz", 20,"location"));
                                break;
                            }
                        }
//                        when(inner.invoke(/*invoke对象就是get出来的*/ user, "fz")).thenReturn(new User(1L, "fz", 20,"location"));
//                        break;
                        break;
                    }
                }
                System.out.println(JSON.toJSONString(method.invoke(userService, param)));
                System.exit(0);
            }
        }
    }

}
