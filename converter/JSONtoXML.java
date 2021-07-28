package converter;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JSONtoXML extends Converter{
    /**
     * Takes a JSON string to be parsed and determines which method to call
     * @param element JSON to be parsed
     */
    @Override
    void parseElement(String element) {
        if (element.contains("@") || element.contains("#")) {
            parseAttributeElement(element);
        } else {
            parseSingleElement(element);
        }
    }

    /**
     * Parses a JSON element without attributes into a key/value pair to be
     * passed on to be printed
     * @param element JSON element
     */
    @Override
    void parseSingleElement(String element) {
        Map<String, String> map = new LinkedHashMap<>();
        String[] splits = element.split(":");
        String key = splits[0].trim()
                .replace("{", "")
                .replace("\"", "");
        String value = splits[1].trim()
                .replace("\"", "")
                .replace("}", "");
        map.put(key, value);
        new Output(true).printSingleElement(map);
    }

    /**
     * Parse a JSON element that has attributes into key/value pairs to be
     * translated into XML notation
     * @param element JSON to be parsed
     */
    @Override
    void parseAttributeElement(String element) {
        Matcher property = Pattern.compile("(?<=\")\\w+(?=\"\\s+:\\s+\\{\\s)")
                .matcher(element);
        Matcher attributes = Pattern.compile("\"[@#]\\w+\"\\s+:\\s+.+?(?=,\\s+|\\s+})")
                .matcher(element);

        Map<String, String> map = new LinkedHashMap<>();
        if (property.find()) {
            map.put(property.group(), "null");
        }

        while (attributes.find()) {
            String[] attribs = attributes.group()
                    .replace("\"", "")
                    .split(":");
            map.put(attribs[0].substring(1).trim(), attribs[1].trim());
        }

        new Output(true).printAttributeElement(map);
    }
}
