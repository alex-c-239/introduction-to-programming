package myscanner;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;

public class MyScanner implements Closeable {

    private final Reader reader;

    private final StringBuilder sb;

    private static final int initBufferSize = 1024;
    private int capacity;
    private char[] buffer;

    private int curIndex = -1;

    private boolean endReached = false;

    private static final int STANDARD_RADIX = 10;

    private boolean isNumeric(char symbol) {
        return Character.isDigit(symbol) || Character.isLetter(symbol) || symbol == '-';
    }

    private boolean isWord(char symbol) {
        return Character.isLetter(symbol) || symbol == '\'' || Character.getType(symbol) == Character.DASH_PUNCTUATION;
    }

    public boolean isLineBreak(char symbol) {
        return symbol == '\n' || symbol == '\r' || symbol == '\u0085' || symbol == '\u2028' || symbol == '\u2029';
    }

    public MyScanner(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        sb = new StringBuilder();
        buffer = new char[initBufferSize];
        capacity = initBufferSize;
        fillBuffer();
    }

    public MyScanner(String input) {
        this(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    public MyScanner(File input) throws FileNotFoundException {
        this(new FileInputStream(input));
    }

    private void fillBuffer() {
        if (endReached) {
            capacity = curIndex + 1;
            return;
        }
        try {
            int readLen = reader.read(buffer, curIndex + 1, capacity - curIndex - 1);
            if (readLen == -1) {
                endReached = true;
                capacity = curIndex + 1;
                return;
            }
            capacity = curIndex + 1 + readLen;
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }

    public char readBuffer(boolean cut) {
        while (!endReached && curIndex + 1 >= capacity) {
            if (cut) {
                if (buffer.length - capacity >= initBufferSize) {
                    capacity += initBufferSize;
                } else {
                    curIndex = -1;
                    buffer = Arrays.copyOf(buffer, initBufferSize);
                    capacity = initBufferSize;
                }
            } else {
                capacity *= 2;
                if (buffer.length < capacity) {
                    buffer = Arrays.copyOf(buffer, capacity);
                }
            }
            fillBuffer();
        }

        if (curIndex + 1 >= capacity) {
            return (char) 0;
        }

        curIndex++;
        return buffer[curIndex];
    }

    public char readBuffer() {
        return readBuffer(true);
    }

    public boolean hasNext() {
        int oldIndex = curIndex;
        readBuffer(false);
        curIndex = oldIndex;
        return !(endReached && curIndex + 1 >= capacity);
    }

    public void toNextLine() {
        char symbol = readBuffer();
        while (hasNext() && !isLineBreak(symbol)) {
            symbol = readBuffer();
        }

        if (!hasNext()) {
            return;
        }

        if (symbol == '\r') {
            symbol = readBuffer();
            if (symbol != '\n') {
                curIndex--;
            }
        }
    }

    private String nextToken(Function<Character, Boolean> isGood, Function<Character, Boolean> isBreak, boolean cut) {
        int resetIndex = curIndex;
        sb.setLength(0);

        while (checkNextChar(c -> !isBreak.apply(c)) && checkNextChar(c -> !isGood.apply(c))) {
            readBuffer(cut);
        }
        while (checkNextChar(isGood)) {
            sb.append(readBuffer(cut));
        }

        if(!cut) {
            curIndex = resetIndex;
        }
        return sb.toString();
    }

    public boolean hasNextNumber() {
        return !nextToken(this::isNumeric, this::isLineBreak, false).equals("");
    }

    public boolean hasNextWord() {
        return !nextToken(this::isWord, this::isLineBreak, false).equals("");
    }

    public String nextNumber() {
        return nextToken(this::isNumeric, (symbol) -> false, true);
    }

    public String nextWord() {
        return nextToken(this::isWord, (symbol) -> false, true);
    }

    public int nextInt(int radix) {
        return Integer.parseInt(nextNumber(), radix);
    }

    public int nextInt() {
        return nextInt(STANDARD_RADIX);
    }

    public long nextLong(int radix) {
        return Long.parseLong(nextNumber(), radix);
    }

    public long nextLong() {
        return nextInt(STANDARD_RADIX);
    }

    public void skipLineBreaks() {
        while (checkNextChar(this::isLineBreak)) {
            readBuffer();
        }
    }

    public void skipSpaces() {
        while (checkNextChar(Character::isWhitespace)) {
            readBuffer();
        }
    }

    private char checkNextChar() {
        int resetIndex = curIndex;
        char symbol = readBuffer(false);
        curIndex = resetIndex;
        return symbol;
    }

    public boolean checkNextChar(char expectedSymbol) {
        return hasNext() && checkNextChar() == expectedSymbol;
    }

    public boolean checkNextChar(Function<Character, Boolean> isGood) {
        return hasNext() && isGood.apply(checkNextChar());
    }

    public char nextChar() {
        return readBuffer();
    }

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }
}