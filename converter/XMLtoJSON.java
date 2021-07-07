package converter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class XMLtoJSON extends Converter{
    /**
     * Takes an XML string to be parsed and determines which method to call
     * @param element XML to be parsed
     */
    @Override
    void parseElement(String element) {
        if (element.contains("=")) {
            parseAttributeElement(element);
        } else {
            parseSingleElement(element);
        }
    }

    /**
     * Parses an XML element without attributes into a key/value pair to be
     * passed on to be printed
     * @param element XML element
     */
    @Override
    void parseSingleElement(String element) {
        Map<String, String> map = new LinkedHashMap<>();
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

    /**
     * Print an XML element without attributes in JSON format
     * @param map map of XML element
     */
    @Override
    void printSingleElement(Map<String, String> map) {
        for (String key : map.keySet()) {
            if (map.get(key).equals("null")) {
                System.out.printf("{\"%s\": null}\n", key);
            } else {
                System.out.printf("{\"%s\": \"%s\"}\n", key, map.get(key));
            }
        }
    }

    /**
     * Parse an XML element that has attributes into key/value pairs to be
     * translated into JSON notation
     * @param element XML to be parsed
     */
    @Override
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

    /**
     * Print a map of an XML element with attributes in JSON format
     * @param map map of XML element
     */
    @Override
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
