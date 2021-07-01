package converter;

public class Parser {
    static Converter getParser(String textToParse) {
        return textToParse.startsWith("<") ?
                new XMLtoJSON() :
                new JSONtoXML();
    }
}
