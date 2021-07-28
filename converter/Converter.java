package converter;

public abstract class Converter {

    abstract void parseElement(String element);

    abstract void parseSingleElement(String element);

    abstract void parseAttributeElement(String element);
}
