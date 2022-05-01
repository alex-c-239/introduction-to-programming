import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;

public class IdealPyramid {
    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(System.in);
        int n = scanner.nextInt();
        int xl = -1, xr = -1, yl = -1, yr = -1;
        for(int i = 0; i < n; ++i) {
            Pyramid pyramid = new Pyramid();
            pyramid.setX(scanner.nextInt());
            pyramid.setY(scanner.nextInt());
            pyramid.setH(scanner.nextInt());
            if(i == 0) {
                xl = pyramid.getX() - pyramid.getH();
                xr = pyramid.getX() + pyramid.getH();
                yl = pyramid.getY() - pyramid.getH();
                yr = pyramid.getY() + pyramid.getH();
            } else {
                xl = Math.min(xl, pyramid.getX() - pyramid.getH());
                xr = Math.max(xr, pyramid.getX() + pyramid.getH());
                yl = Math.min(yl, pyramid.getY() - pyramid.getH());
                yr = Math.max(yr, pyramid.getY() + pyramid.getH());
            }
        }
        System.out.println(
                ((xl + xr) / 2) + " " +
                ((yl + yr) / 2) + " " +
                ((int)(Math.ceil(Math.max(xr - xl, yr - yl) / 2.0)))
        );
    }
}

class Pyramid {
    private int x;
    private int y;
    private int h;

    public Pyramid() {
        x = y = h = 0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setH(int h) {
        this.h = h;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getH() {
        return h;
    }
}

class MyScanner implements Closeable {

    private final Reader reader;

    private final StringBuilder sb;

    private static final int initBufferSize = 1024;
    private int capacity = initBufferSize;
    private char[] buffer;

    private int curIndex = -1;
    private int resetIndex = -1;

    private boolean endReached = false;

    private static final int STANDARD_RADIX = 10;

    private boolean isNumeric(char symbol) {
        return Character.isDigit(symbol) || Character.isLetter(symbol) || symbol == '-';
    }

    private boolean isWord(char symbol) {
        return Character.isLetter(symbol) || symbol == '\'' || Character.getType(symbol) == Character.DASH_PUNCTUATION;
    }

    private boolean isLineBreak(char symbol) {
        return symbol == '\n' || symbol == '\r' || symbol == '\u0085' || symbol == '\u2028' || symbol == '\u2029';
    }

    public MyScanner(InputStream input) {
        reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));
        sb = new StringBuilder();
        buffer = new char[initBufferSize];
        fillBuffer();
    }

    public MyScanner(String input) {
        this(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
    }

    public MyScanner(File input) throws FileNotFoundException {
        this(new FileInputStream(input));
    }

    private void fillBuffer() {
        if(endReached) {
            capacity = curIndex + 1;
            return;
        }
        try {
            int readLen = reader.read(buffer, curIndex + 1, capacity - curIndex - 1);
            if(readLen == -1) {
                endReached = true;
                capacity = curIndex + 1;
                return;
            }
            capacity = curIndex + 1 + readLen;
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }

    private void mark() {
        resetIndex = curIndex;
    }

    private void reset() {
        curIndex = resetIndex;
    }

    private char readBuffer(boolean cut) {
        while(!endReached && curIndex + 1 >= capacity) {
            if(cut) {
                if(buffer.length - capacity >= initBufferSize) {
                    capacity += initBufferSize;
                } else {
                    curIndex = -1;
                    buffer = Arrays.copyOf(buffer, initBufferSize);
                    capacity = initBufferSize;
                }
            } else {
                capacity *= 2;
                if(buffer.length < capacity) {
                    buffer = Arrays.copyOf(buffer, capacity);
                }
            }

            fillBuffer();
        }

        if(curIndex + 1 >= capacity) {
            return (char)0;
        }

        curIndex++;
        return buffer[curIndex];
    }

    private char readBuffer() {
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
        mark();

        char symbol = readBuffer(cut);
        while (hasNext() && !isBreak.apply(symbol) && !isGood.apply(symbol)) {
            symbol = readBuffer(cut);
        }

        if (!isGood.apply(symbol)) {
            if(!cut) {
                reset();
            }
            return null;
        }

        sb.setLength(0);
        while (isGood.apply(symbol)) {
            sb.append(symbol);
            if (cut) {
                mark();
            }
            if (!hasNext()) {
                break;
            }
            symbol = readBuffer(cut);
        }

        reset();
        return sb.toString();
    }

    public boolean hasNextNumber() {
        String number = nextToken(this::isNumeric, this::isLineBreak, false);
        return (number != null);
    }

    public boolean hasNextWord() {
        String word = nextToken(this::isWord, this::isLineBreak, false);
        return (word != null);
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

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }
}