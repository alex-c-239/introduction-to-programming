import myscanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WsppSecondG {

    public static void main(String[] args) {
        Map<String, Triple> result = new LinkedHashMap<>();
        int wordsCounter = 0;
        try {
            MyScanner scanner = new MyScanner(new File(args[0]));
            while (scanner.hasNext()) {
                for (String word : result.keySet()) {
                    result.get(word).setLineCounter(0);
                }
                while (scanner.hasNextWord()) {
                    ++wordsCounter;
                    String word = scanner.nextWord().toLowerCase();
                    if (result.get(word) == null) {
                        result.put(word, new Triple(1, 1, new IntList()));
                    } else {
                        result.get(word).addCounters();
                    }
                    if (result.get(word).getLineCounter() % 2 == 0) {
                        result.get(word).list.add(wordsCounter);
                    }
                }
                scanner.toNextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found : " + e.getMessage());
        }

        try (BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8)
        )) {
            for (String word : result.keySet()) {
                writer.write(word + " " + result.get(word).getMainCounter());
                for (int i = 0; i < result.get(word).list.size(); ++i) {
                    writer.write(" " + result.get(word).list.get(i));
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }
}