package converter;

import java.util.Map;

public abstract class Converter {

    abstract void parseElement(String element);

    abstract void parseSingleElement(String element);

    abstract void printSingleElement(Map<String, String> element);
}
