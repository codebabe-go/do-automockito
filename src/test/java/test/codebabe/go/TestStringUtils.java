package test.codebabe.go;

import com.codebabe.util.StringUtils;
import org.junit.Test;

/**
 * author: code.babe
 * date: 2016-12-01 14:27
 */
public class TestStringUtils {

    @Test
    public void testField() {
        String method = "getName";
        System.out.println(StringUtils.fieldByMethod(method, StringUtils.GETTER));
    }

    @Test
    public void testReverse() {
        String name = "name";
        System.out.println(StringUtils.reverseCaseByIndex(name, 0));
    }

    @Test
    public void testTrim() {
        System.out.println(StringUtils.trimBesideFigure("a", "\""));
    }

}
