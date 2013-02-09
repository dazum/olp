package com.dazum.parser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import com.dazum.parser.OLP;

/**
 * Unit test for simple App.
 */
public class OLPTest extends TestCase {

    /**
     * Json Log List
     *
     */
    List<String> logs = null;

    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public OLPTest(String testName) {
        super(testName);
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite() {
        return new TestSuite(OLPTest.class);
    }

    /**
     * Set up before tests
     */
    public void setUp() {
        try {
            BufferedReader buffer = new BufferedReader(
                    new FileReader("src/test/resources/test.log"));
            logs = new ArrayList<String>();
            String line = new String();
            while ((line = buffer.readLine()) != null) {
                logs.add(line);
            }
            buffer.close();
        } catch (Exception e) {
        }
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp() {
        Map<String, String> map = OLP.getMap(logs.get(0));
        assertEquals(7, map.size());
        assertEquals("mojiretsu", map.get("string"));
        assertEquals("true", map.get("t"));
        assertEquals("false", map.get("f"));
        assertEquals("null", map.get("n"));
        assertEquals("{\"a\":\"A\",\"b\":\"B\"}", map.get("obj"));
        assertEquals("[1,2,3]", map.get("arr"));
        assertEquals("dell", map.get("pc"));

        List<String> list = OLP.getList(map.get("arr"));
        assertEquals(3, list.size());
        assertEquals("1", list.get(0));
        assertEquals("2", list.get(1));
        assertEquals("3", list.get(2));
    }

}
