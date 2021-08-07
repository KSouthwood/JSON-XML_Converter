package converter;

import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseXML extends Converter {
    private final Matcher element;
    private final Matcher attrib;
    private final Matcher value;

    ParseXML(String inputToParse) {
        element = Pattern.compile("(?<=<).+?(?=>)").matcher(inputToParse);
        attrib = Pattern.compile("\\w+\\s*=\\s*\"\\w+\"").matcher(inputToParse);
        value = Pattern.compile("(?<=>).*?(?=<)").matcher(inputToParse);
    }

    Element parseElement() {
        Element root = null;

        if (element.find()) {
            root = new Element(getNodeName(element.group()));
            root.setPath(new LinkedList<>());
            checkForAttributes(root);
            parseValue(root);
            parseNode(root);
        }

        return root;
    }

    private void parseNode(Element parent) {
        while (element.find()) {
            String name = getNodeName(element.group());
            if (name.startsWith("/")) {
                return;
            }

            Element node = new Element(name);
            parent.addChild(node);
            node.setPath(parent.getPath());
            checkForAttributes(node);

            if (element.group().endsWith("/")) {
                node.setValue(null);
                continue;
            }

            parseValue(node);

            parseNode(node);
        }
    }

    private String getNodeName(String parseString) {
        String name = parseString;
        if (parseString.contains(" ")) {
            name = parseString.substring(0, parseString.indexOf(" "));
        }
        return name;
    }

    private void checkForAttributes(Element node) {
        if (element.group().contains("=")) {
            parseAttributes(element.start(), element.end(), node);
        }
    }

    private void parseAttributes(int start, int end, Element node) {
        attrib.region(start, end);
        while (attrib.find()) {
            String[] attribute = attrib.group().split("\\s*=\\s*");
            node.addAttribute(attribute[0], attribute[1].replace("\"", ""));
        }
    }

    private void parseValue(Element node) {
        if (value.find(element.end())) {
            node.setValue(value.group().isBlank() ? "" : value.group());
        }
    }

}
