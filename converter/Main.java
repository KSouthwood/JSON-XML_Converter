package converter;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        JSON json = new JSON();
        XML xml = new XML();
        String input = readLine().trim();
        if (input.startsWith("<")) {
            json.printElement(xml.parseElement(input));
        } else if (input.startsWith("{")) {
            xml.printElement(json.parseElement(input));
        } else {
            System.out.println("Invalid JSON or XML syntax.");
        }
    }

    static String readLine() {
        final Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
