import myscanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Wspp {
    public static void main(String[] args) {
        Map<String, IntList> ans = new LinkedHashMap<>();
        try {
            MyScanner scanner = new MyScanner(new File(args[0]));
            int wordCounter = 0;
            while (scanner.hasNext()) {
                while (scanner.hasNextWord()) {
                    ++wordCounter;
                    String word = scanner.nextWord().toLowerCase();
                    if (ans.get(word) == null) {
                        ans.put(word, new IntList(wordCounter));
                    } else {
                        ans.get(word).add(wordCounter);
                    }
                }
                scanner.toNextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found : " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8))) {
            String[] words = ans.keySet().toArray(new String[0]);
            for (String word : words) {
                writer.write(word + " " + ans.get(word).size());
                for (int i = 0; i < ans.get(word).size(); ++i) {
                    writer.write(" " + ans.get(word).get(i));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }
}