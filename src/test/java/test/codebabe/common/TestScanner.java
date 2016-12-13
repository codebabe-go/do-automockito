package test.codebabe.common;

import com.codebabe.common.MockCallScanner;
import org.junit.Test;

import java.io.IOException;

/**
 * author: code.babe
 * date: 2016-12-01 21:51
 */
public class TestScanner {

    @Test
    public void testScanner() throws IOException, ClassNotFoundException {
        MockCallScanner mockCallScanner = new MockCallScanner();
        System.out.println(mockCallScanner.scan4MockCall("/Users/codebabe/Coding/do-automockito/src/test/java/test/codebabe/common/scanner.txt"));
    }

}
