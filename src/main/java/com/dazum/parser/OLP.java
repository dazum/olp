package com.dazum.parser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.codehaus.jackson.*;

/**
 * OLP
 *
 */
public class OLP {

    public static Map<String, String> getMap(String log) {
        Map<String, String> map = new HashMap<String, String>();
        try {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createJsonParser(log);

            if (parser.nextToken() == JsonToken.START_OBJECT) {
                while (parser.nextToken() != JsonToken.END_OBJECT) {
                    String key = parser.getCurrentName();
                    parser.nextToken();
                    String value = getElement(parser, log);
                    map.put(key, value);
                }
            }
        } catch (IOException e) {
        }
        return map;
    }

    public static List<String> getList(String log) {
        List<String> list =  new ArrayList<String>();
        try {
            JsonFactory factory = new JsonFactory();
            JsonParser parser = factory.createJsonParser(log);

            if (parser.nextToken() == JsonToken.START_ARRAY) {
                while (parser.nextToken() != JsonToken.END_ARRAY) {
                    String element = getElement(parser, log);
                    list.add(element);
                }
            }
        } catch (IOException e) {
        }
        return list;
    }

    private static String getElement(JsonParser parser, String log) throws IOException {
        String element = new String();
        switch (parser.getCurrentToken()) {
            case START_OBJECT:
                element = getObject(parser, log);
                break;
            case START_ARRAY:
                element = getArray(parser, log);
                break;
            case VALUE_STRING:
                element = parser.getText();
                break;
            case VALUE_NUMBER_INT:
                element = String.valueOf(parser.getIntValue());
                break;
            case VALUE_NUMBER_FLOAT:
                element = String.valueOf(parser.getDoubleValue());
                break;
            case VALUE_TRUE:
                element = "true";
                break;
            case VALUE_FALSE:
                element = "false";
                break;
            case VALUE_NULL:
                element = "null";
                break;
        }
        return element;
    }

    private static String getObject(JsonParser parser, String log) throws IOException {
        int start = 1;
        int begin = (int) parser.getCurrentLocation().getCharOffset();
        while (start != 0) {
            JsonToken token = parser.nextToken();
            if (token == JsonToken.END_OBJECT) {
                start--;
            } else if (token == JsonToken.START_OBJECT) {
                start++;
            }
        }
        int end = (int) parser.getCurrentLocation().getCharOffset() + 1;
        return log.substring(begin, end);
    }

    private static String getArray(JsonParser parser, String log) throws IOException {
        int start = 1;
        int begin = (int) parser.getCurrentLocation().getCharOffset();
        while (start != 0) {
            JsonToken token = parser.nextToken();
            if (token == JsonToken.END_ARRAY) {
                start--;
            } else if (token == JsonToken.START_ARRAY) {
                start++;
            }
        }
        int end = (int) parser.getCurrentLocation().getCharOffset() + 1;
        return log.substring(begin, end);
    }

}
