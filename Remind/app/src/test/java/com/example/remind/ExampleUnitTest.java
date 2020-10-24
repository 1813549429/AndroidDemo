package com.example.remind;

import com.example.remind.utils.JsonUtil;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
        List<String> list = new ArrayList<>();
        for (int i=0; i<10; i++) {
            String s = "item" + i;
            list.add(s);
        }
        String jsonData = JsonUtil.strListToJson(list);
        System.out.println(jsonData);
        List<String> ss = JsonUtil.jsonToStrList(jsonData);
        for (String s :
                ss) {
            System.out.println(s);
        }
    }
}