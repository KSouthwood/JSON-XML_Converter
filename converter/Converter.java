package converter;

import java.util.Map;

public abstract class Converter {

    abstract void parseElement(String element);

    abstract void parseSingleElement(String element);

    abstract void printSingleElement(Map<String, String> map);

    abstract void parseAttributeElement(String element);

    abstract void printAttributeElement(Map<String, String> map);
}
