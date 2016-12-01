package test.codebabe.parse;

import com.alibaba.fastjson.JSON;
import com.codebabe.parse.OWLExportParser;
import com.codebabe.parse.Parser;
import org.junit.Test;
import test.codebabe.model.User;

import java.io.IOException;
import java.util.List;

/**
 * author: code.babe
 * date: 2016-12-01 17:20
 */
public class TestParse {

    @Test
    public void testParse() throws IOException {
        Parser<User> parser = new OWLExportParser<>();
        List<User> users = parser.parseData("/Users/codebabe/Desktop/user.txt", User.class);
        System.out.println(JSON.toJSONString(users));
    }

}
