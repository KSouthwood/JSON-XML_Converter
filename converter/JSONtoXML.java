package converter;

import java.util.HashMap;
import java.util.Map;

public class JSONtoXML extends Converter{
    Map<String, String> parseElement(String element) {
        Map<String, String> map = new HashMap<>();
        String[] splits = element.split(":");
        String key = splits[0].trim()
                .replace("{", "")
                .replace("\"", "");
        String value = splits[1].trim()
                .replace("\"", "")
                .replace("}", "");
        map.put(key, value);
        return map;
    }

    void printElement(Map<String, String> element) {
        for (String key : element.keySet()) {
            if (element.get(key).equals("null")) {
                System.out.printf("<%s/>\n", key);
            } else {
                System.out.printf("<%1$s>%2$s</%1$s>\n", key, element.get(key));
            }
        }
    }
}
