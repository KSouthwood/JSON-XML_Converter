package converter;

import java.util.Map;

public abstract class Converter {

    abstract Map<String, String> parseElement(String element);

    abstract void printElement(Map<String, String> element);
}
