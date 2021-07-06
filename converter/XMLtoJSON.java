package converter;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLtoJSON extends Converter{
    void parseElement(String xml) {
        if (xml.contains("=")) {
            parseAttributeElement(xml);
        } else {
            parseSingleElement(xml);
        }
    }

    void parseSingleElement(String element) {
        Map<String, String> map = new HashMap<>();
        if (element.endsWith("/>")) {
            map.put(element.substring(1, element.length() - 2), "null");
        } else {
            int begin = element.indexOf(">");
            int end = element.lastIndexOf("<");
            map.put(element.substring(1, begin),
                    element.substring(begin + 1, end));
        }

        printSingleElement(map);
    }

    void printSingleElement(Map<String, String> element) {
        for (String key : element.keySet()) {
            if (element.get(key).equals("null")) {
                System.out.printf("{\"%s\": null}\n", key);
            } else {
                System.out.printf("{\"%s\": \"%s\"}\n", key, element.get(key));
            }
        }
    }

    void parseAttributeElement(String element) {
        Map<String, String> map = new LinkedHashMap<>();
        String property = element.substring(1, element.indexOf(" "));
        map.put(property, "##attributes");
        Matcher attrib = Pattern.compile("\\w+\\s=\\s\"\\w+\"").matcher(element);
        while (attrib.find()) {
            String[] attribute = attrib.group().trim()
                    .replace(" ", "").split("=");
            map.put("@" + attribute[0], attribute[1].replace("\"", ""));
        }
        if (element.endsWith("/>")) {
            map.put("#" + property, "null");
        } else {
            int begin = element.indexOf(">");
            int end = element.lastIndexOf("<");
            map.put("#" + property, element.substring(begin + 1, end));
        }
        printAttributeElement(map);
    }

    void printAttributeElement(Map<String, String> map) {
        String spaces = "            ";
        int indent = 0;
        int keys = 0;

        System.out.println("{");
        indent += 4;
        for (String key : map.keySet()) {
            if (key.startsWith("@")) {
                System.out.printf("%s\"%s\" : \"%s\"%s\n",
                        spaces.substring(0, indent),
                        key,
                        map.get(key),
                        keys < map.size() ? "," : "");
                keys++;
            } else if (key.startsWith("#")) {
                System.out.printf("%s\"%s\" : \"%s\"\n",
                        spaces.substring(0, indent),
                        key,
                        map.get(key).equals("null") ? "null" : map.get(key));
                indent -= 4;
            } else {
                System.out.printf("%s\"%s\" : {\n",
                        spaces.substring(0, indent),
                        key);
                indent += 4;
            }
        }
        System.out.println(spaces.substring(0, indent) + "}\n}");
    }
}
