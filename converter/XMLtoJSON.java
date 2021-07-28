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

        new Output(false).printSingleElement(map);
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
        new Output(false).printAttributeElement(map);
    }

}
