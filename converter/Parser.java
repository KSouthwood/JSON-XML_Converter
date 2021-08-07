package converter;

public class Parser {
    static Converter getParser(String textToParse) {
        return textToParse.startsWith("<") ?
                new ParseXML(textToParse) :
                new ParseJSON(textToParse);
    }
}
