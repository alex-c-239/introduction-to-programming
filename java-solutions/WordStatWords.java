import myscanner.MyScanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class WordStatWords {

    public static void main(String[] args) {
        List<String> words = new ArrayList<>();
        Map<String, Integer> map = new HashMap<>();
        
        try {
            MyScanner scanner = new MyScanner(new File(args[0]));

            while (scanner.hasNext()) {
                while (scanner.hasNextWord()) {
                    String word = scanner.nextWord().toLowerCase();
                    if(map.get(word) == null) {
                        map.put(word, 1);
                        words.add(word);
                    } else {
                        map.put(word, map.get(word) + 1);
                    }
                }
                scanner.toNextLine();
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found : " + e.getMessage());
        }

        try {
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8));
            Collections.sort(words);
            try {
                for (String word : words) {
                    writer.write(word + " " + map.get(word));
                    writer.newLine();
                }
            } catch (IOException e) {
                System.out.println("I/O Exception : " + e.getMessage());
            } finally {
                writer.close();
            }
        } catch (FileNotFoundException e) {
            System.out.println("Output file not found : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }
}