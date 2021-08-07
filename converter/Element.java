package converter;

import java.util.*;

public class Element {
    private final List<String> path;
    private final String name;
    private final Map<String, String> attributes;
    private String value;
    private final List<Element> children;

    Element(String name) {
        this.name = name;
        this.attributes = new LinkedHashMap<>();
        this.children = new ArrayList<>();
        this.value = null;
        this.path = new LinkedList<>();
    }

    public boolean hasAttributes() {
        return !attributes.isEmpty();
    }

    public Map<String, String> getAttributes() {
        return attributes;
    }

    public void addAttribute(String key, String value) {
        attributes.put(key, value);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean hasChildren() {
        return !children.isEmpty();
    }

    public List<Element> getChildren() {
        return children;
    }

    public void addChild(Element node) {
        children.add(node);
        if ("".equals(getValue())) {
            setValue(null);
        }
    }

    public List<String> getPath() {
        return path;
    }

    public void setPath(List<String> path) {
        this.path.addAll(path);
        this.path.add(name);
    }
}