import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.function.Function;

public class HighLoadDatabase {
    public static void main(String[] args) {
        MyScanner scanner = new MyScanner(System.in);

        int n = Integer.parseInt(scanner.nextNumber());
        int[] a = new int[n + 1];
        int[] prefixSumA = new int[n + 1];
        prefixSumA[0] = 0;
        int maxA = 0;
        for (int i = 1; i <= n; ++i) {
            a[i] = Integer.parseInt(scanner.nextNumber());
            if(a[i] > maxA) {
                maxA = a[i];
            }
            prefixSumA[i] = prefixSumA[i - 1] + a[i];
        }

        int q = Integer.parseInt(scanner.nextNumber());

        int[] t = new int[q];
        for (int i = 0; i < q; ++i) {
            t[i] = Integer.parseInt(scanner.nextNumber());
        }

        scanner.close();

        int aSum = prefixSumA[n];

        int[] f = new int[aSum + 1];

        for (int i = 1; i <= n; ++i) {
            for (int j = prefixSumA[i - 1] + 1; j <= prefixSumA[i]; ++j) {
                f[j] = i;
            }
        }

        int numberOfBathes, b;
        for (int i = 0; i < q; ++i) {
            if (t[i] < maxA) {
                System.out.println("Impossible");
                continue;
            }

            numberOfBathes = 0;
            b = 0;
            while (b < n) {
                ++numberOfBathes;

                if (prefixSumA[b] + t[i] >= aSum) {
                    break;
                }

                b = f[prefixSumA[b] + t[i] + 1] - 1;
            }
            System.out.println(numberOfBathes);
        }
    }
}

class MyScanner implements Closeable {

    private final Reader reader;
    private final StringBuilder sb;
    private char[] buffer;
    private static final int initBufferSize = 1024;
    private int capacity = initBufferSize;
    private int curIndex = -1;
    private int resetIndex = -1;
    private boolean endReached = false;

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
        reader = new BufferedReader(
                new InputStreamReader(
                        new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)),
                        StandardCharsets.UTF_8
                )
        );
        sb = new StringBuilder();
        buffer = new char[initBufferSize];
        fillBuffer();
    }

    private void fillBuffer() {
        if(endReached) {
            capacity = curIndex + 1;
            //buffer = Arrays.copyOf(buffer, curIndex + 1);
            return;
        }
        try {
            int readLen = reader.read(buffer, curIndex + 1, capacity - curIndex - 1);
            if(readLen == -1) {
                endReached = true;
                capacity = curIndex + 1;
                //buffer = Arrays.copyOf(buffer, curIndex + 1);
                return;
            } /*else if(curIndex + 1 + readLen < buffer.length) {
                //buffer = Arrays.copyOf(buffer, curIndex + 1 + readLen);
            }*/
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
        while(!endReached && curIndex + 1 >= capacity/*buffer.length*/) {
            if(cut) {
                if(buffer.length - capacity >= initBufferSize) {
                    capacity += initBufferSize;
                } else {
                    curIndex = -1;
                    buffer = Arrays.copyOf(buffer, initBufferSize);
                    capacity = initBufferSize;
                }
            } else {
                if(buffer.length >= 2 * capacity) {
                    capacity *= 2;
                } else {
                    buffer = Arrays.copyOf(buffer, buffer.length * 2);
                    capacity = buffer.length;
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

    @Override
    public void close() {
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("I/O Exception : " + e.getMessage());
        }
    }
}

