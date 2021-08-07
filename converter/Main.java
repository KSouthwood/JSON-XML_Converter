package converter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        boolean file = args.length == 0 || !args[0].equals("console");
        String input = file ? readFromFile() : readLine();
        Converter parser = Parser.getParser(input);
        new Output(true).printElement(parser.parseElement());
    }

    static String readLine() {
        final Scanner scanner = new Scanner(System.in);
        StringBuilder input = new StringBuilder();
        while (scanner.hasNext()) {
            input.append(scanner.nextLine());
        }
        return input.toString().trim();
    }

    static String readFromFile() {
        StringBuilder input = new StringBuilder();
        String line;

//        try (BufferedReader file = new BufferedReader(new FileReader(".\\JSON - XML converter\\task\\src\\converter\\myTestFile.txt"))) {
        try (BufferedReader file = new BufferedReader(new FileReader("test.txt"))) {
            while ((line = file.readLine()) != null) {
                input.append(line);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }

        return input.toString().trim();
    }
}
