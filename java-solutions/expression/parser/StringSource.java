package expression.parser;

public class StringSource {
    private static final char END = '\0';

    private final String source;
    private int position = 0;

    public StringSource(String source) {
        this.source = source;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean eof() {
        return position >= source.length();
    }

    // Returns current char and moves to the next. If end is reached, returns END symbol.
    public char next() {
        return !eof() ? source.charAt(position++) : END;
    }

    // Returns current char. If end is reached, returns END symbol.
    public char get() {     // Returns current char. If end is reached, returns END symbol.
        return !eof() ? source.charAt(position) : END;
    }

    @Override
    public String toString() {
        return source;
    }
}
