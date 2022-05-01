package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class Md2Html {
    public static void main(String[] args) {
        try (Md2HtmlParser parser = new Md2HtmlParser(new File(args[0]))) {
            String ans = parser.parse();

            try (
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(new FileOutputStream(args[1]), StandardCharsets.UTF_8)
                    )
            ) {
                writer.write(ans);
            } catch (IOException e) {
                System.out.println("I/O Exception : " + e.getMessage());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Input file not found : " + e.getMessage());
        }
    }
}
