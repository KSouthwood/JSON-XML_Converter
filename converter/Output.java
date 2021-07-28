package converter;

import java.util.HashMap;
import java.util.Map;

public class Output {
    private static final Map<String, String> formats = new HashMap<>();
    private final boolean isXML;

    public Output(boolean isXML) {
        this.isXML = isXML;
        if (isXML) {
            setXMLFormats();
        } else {
            setJSONFormats();
        }
    }

    private void setXMLFormats() {
        formats.put("null", "<%s/>\n");
        formats.put("element", "<%1$s>%2$s</%1$s>\n");
    }

    private void setJSONFormats() {
        formats.put("null", "{\"%s\": null}\n");
        formats.put("element", "{\"%s\": \"%s\"}\n");
    }

    void printSingleElement(Map<String, String> element) {
        for (String key : element.keySet()) {
            if (element.get(key).equals("null")) {
                System.out.printf(formats.get("null"), key);
            } else {
                System.out.printf(formats.get("element"), key, element.get(key));
            }
        }
    }

    void printAttributeElement(Map<String, String> map) {
        if (isXML) {
            printXMLAttributeElement(map);
        } else {
            printJSONAttributeElement(map);
        }
    }

    private void printXMLAttributeElement(Map<String, String> map) {
        StringBuilder xml = new StringBuilder();
        String property = "";
        for (String key : map.keySet()) {
            if (property.isBlank()) {
                property = key;
                xml.append("<").append(property);
            } else {
                xml.append(" ").append(key).append(" = \"")
                        .append(map.get(key)).append('"');
            }
        }
        if (map.get(property).equals("null")) {
            xml.append(" />");
        } else {
            xml.append(">").append(map.get(property))
                    .append("</").append(property).append(">");
        }
        System.out.println(xml);
    }

    /**
     * Print a map of an XML element with attributes in JSON format
     * @param map map of XML element
     */
    private void printJSONAttributeElement(Map<String, String> map) {
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
                String value = map.get(key);
                String format = value.equals("null") ?
                        "%s\"%s\" : %s\n" :
                        "%s\"%s\" : \"%s\"\n";
                System.out.printf(format,
                        spaces.substring(0, indent),
                        key,
                        map.get(key));
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
