package test.codebabe.go;

import com.codebabe.common.MockCallScanner;
import com.codebabe.util.ClassUtils;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * author: code.babe
 * date: 2016-12-13 13:31
 */
public class TestClassUtils {

    @Test
    public void test() throws ClassNotFoundException {
        Class clz = ClassUtils.loadClassByName("test.codebabe.go.TestClassUtils");
        System.out.println(clz.getName());
    }

    @Test
    public void testImport() throws IOException, ClassNotFoundException {
        Map<String, Class> map = MockCallScanner.classMapping(new File("/Users/codebabe/Coding/do-automockito/src/test/java/test/codebabe/MockitoTest.java"));
        System.out.println(map.size());
    }

}
