package expression.parser;

import java.util.function.Function;

public class BaseParser {
    private final StringSource source;

    public BaseParser(String source) {
        this.source = new StringSource(source);
    }

    public boolean eof() {
        return source.eof();
    }

    public char next() {
        return source.next();
    }

    public void skipWhitespaces() {
        while (!eof() && Character.isWhitespace(source.get())) {
            source.next();
        }
    }

    public boolean check(char expected) {
        return expected == source.get();
    }

    public boolean check(Function<Character, Boolean> isGood) {
        return !eof() && isGood.apply(source.get());
    }

    public boolean take(char expected) {
        if (check(expected)) {
            next();
            return true;
        }
        return false;
    }

    public boolean take(String expected) {
        int startPos = source.getPosition();
        for (char c : expected.toCharArray()) {
            if(!take(c)) {
                source.setPosition(startPos);
                return false;
            }
        }
        return true;
    }

    public boolean between(char left, char right) {
        return left <= source.get() && source.get() <= right;
    }

    public String getSource() {
        return source.toString();
    }
}
