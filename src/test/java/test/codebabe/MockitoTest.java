package test.codebabe;

import com.alibaba.fastjson.JSON;
import com.codebabe.MockGo;
import com.codebabe.model.Entity;
import com.codebabe.model.PrintType;
import com.codebabe.parse.OpenIt;
import com.codebabe.util.ClassUtils;
import com.codebabe.util.StringUtils;
import org.junit.Before;
import org.junit.Test;
import test.codebabe.api.UserService;
import test.codebabe.model.User;

import javax.annotation.Resource;
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
        User user = userService.getUser().test("fz");
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
        Map<String, Entity> mapper = new HashMap<>();

        Class<UserService> userServiceClass = UserService.class;
        UserService userService = ClassUtils.newInstance(userServiceClass);

        // 用例为了保证时序 使用两次循环来进行操作
        for (Method method : userServiceClass.getMethods()) {
            if (StringUtils.equals(method.getName(), "setUser")) {
                User user = mock(User.class);
                method.invoke(userService, user);
                mapper.put("user", new Entity("user", user, user.getClass()));
            }
        }

        String param = "fz";

        for (Method method : userServiceClass.getMethods()) {
            if (StringUtils.equals(method.getName(), "get")) {
                // 实际上是取获得user的mock数据
                Entity entity = mapper.get("user");
                Class userClass = entity.getClz();
                for (Method inner : userClass.getMethods()) {
                    if (inner.getName().equals("test")) {
//                        for (Method getter : userServiceClass.getMethods()) {
//                            if (getter.getName().equals("getUser")) {
                                // success
//                                when(inner.invoke(/*invoke对象就是get出来的*/ getter.invoke(userService), param)).thenReturn(new User(1L, "fz", 20,"location"));
//                                break;
//                            }
//                        }
                        when(inner.invoke(/*invoke对象就是get出来的*/ entity.getInstance(), param)).thenReturn(new User(1L, "fz", 20,"location"));
                        break;
                    }
                }
                System.out.println(JSON.toJSONString(method.invoke(userService, param)));
                System.exit(0);
            }
        }
    }

    /**
     * 测试主要方法, 这里测试的是不涉及上下mock数据的
     * @throws Exception
     */
    @Test
    public void testMain() throws Exception {
        OpenIt openIt = new MockGo(PrintType.Type.S_OUT);
        Map<String, String> pathMap = new HashMap<>();
        pathMap.put("test", "/Users/codebabe/Desktop/user.txt");
        pathMap.put(UserService.class.getName(), "/Users/codebabe/Coding/do-automockito/src/test/java/test/codebabe/api/UserService.java");
        openIt.go4Unflowerring(UserService.class, Resource.class, "get", pathMap);
    }

}
