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

    @Test
    public void testSub() {
        String imp = "import com.codebabe.util.StringUtils;";
        String packageName = StringUtils.substringBetween(imp, " ", ";");
        System.out.println(packageName);
        System.out.println(StringUtils.substringAfterLast(packageName, "."));

        String line = "List<String> imp = \"import com.codebabe.util.StringUtils;\";";
        String prefix = StringUtils.substringBefore(line, " ");
        String result = "";
        if (prefix.contains("<")) {
            result = StringUtils.substringBetween(prefix, "<", ">");
        } else {
            result = prefix;
        }
        System.out.println(result);
    }

}
