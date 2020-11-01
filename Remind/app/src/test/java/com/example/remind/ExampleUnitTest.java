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
        List<String> s1 = new ArrayList<>();
        s1.add("23424");
        List<List<String>> list = new ArrayList<>();
        list.add(s1);
        System.out.println(list.get(0).get(0));

    }
}