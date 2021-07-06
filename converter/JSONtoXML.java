package converter;

import java.util.HashMap;
import java.util.Map;

public class JSONtoXML extends Converter{
    @Override
    void parseElement(String element) {
        parseSingleElement(element);
    }

    void parseSingleElement(String element) {
        Map<String, String> map = new HashMap<>();
        String[] splits = element.split(":");
        String key = splits[0].trim()
                .replace("{", "")
                .replace("\"", "");
        String value = splits[1].trim()
                .replace("\"", "")
                .replace("}", "");
        map.put(key, value);
        printSingleElement(map);
    }

    void printSingleElement(Map<String, String> element) {
        for (String key : element.keySet()) {
            if (element.get(key).equals("null")) {
                System.out.printf("<%s/>\n", key);
            } else {
                System.out.printf("<%1$s>%2$s</%1$s>\n", key, element.get(key));
            }
        }
    }
}
