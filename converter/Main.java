package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        String input = readLine().trim();
        Converter parser = Parser.getParser(input);
        parser.parseElement(input);
    }

    static String readLine() {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        while (scanner.hasNext()) {
            input.append(scanner.nextLine());
        }
        return input.toString();
    }
}
