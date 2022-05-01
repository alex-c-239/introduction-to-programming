import myscanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordStatInput {

    public static void main(String[] args) {
        Map<String, Integer> ans = new LinkedHashMap<>();

        try {
            MyScanner scanner = new MyScanner(new File(args[0]));
            while (scanner.hasNext()) {
                while (scanner.hasNextWord()) {
                    ans.merge(scanner.nextWord().toLowerCase(), 1, Integer::sum);
                }
                scanner.toNextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found : " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            for (String word : ans.keySet()) {
                writer.write(word + " " + ans.get(word));
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }
}