package converter;

import java.util.HashMap;
import java.util.Map;

public class XMLtoJSON extends Converter{
    Map<String, String> parseElement(String element) {
        Map<String, String> map = new HashMap<>();
        if (element.endsWith("/>")) {
            map.put(element.substring(1, element.length() - 2), "null");
        } else {
            int begin = element.indexOf(">");
            int end = element.lastIndexOf("<");
            map.put(element.substring(1, begin),
                    element.substring(begin + 1, end));
        }
        return map;
    }

    void printElement(Map<String, String> element) {
        for (String key : element.keySet()) {
            if (element.get(key).equals("null")) {
                System.out.printf("{\"%s\": null}\n", key);
            } else {
                System.out.printf("{\"%s\": \"%s\"}\n", key, element.get(key));
            }
        }
    }
}
