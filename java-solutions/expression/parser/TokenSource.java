package expression.parser;

public class TokenSource {
    private final BaseParser parser;
    private String stringToken;
    private Token currentToken;

    public TokenSource(String source) {
        this.parser = new BaseParser(source);
    }

    private Token getEnd() {
        stringToken = null;
        return currentToken = Token.END;
    }

    private Token getVariable() {
        stringToken = String.valueOf(parser.next());
        return currentToken = Token.VARIABLE;
    }

    private Token getConst() {
        StringBuilder ans = new StringBuilder();
        while (parser.between('0', '9')) {
            ans.append(parser.next());
        }
        stringToken = ans.toString();
        return currentToken = Token.CONST;
    }

    private Token getBracket(boolean isOpenBracket) {
        if (isOpenBracket) {
            stringToken = "(";
            return currentToken = Token.BRACKET_OPEN;
        }
        stringToken = ")";
        return currentToken = Token.BRACKET_CLOSE;
    }

    private Token getPlus() {
        stringToken = "+";
        return currentToken = Token.PLUS;
    }

    private Token getMinus() {
        stringToken = "-";
        return currentToken = Token.MINUS;
    }

    private Token getMultiply() {
        stringToken = "*";
        return currentToken = Token.MULTIPLY;
    }

    private Token getDivide() {
        stringToken = "/";
        return currentToken = Token.DIVIDE;
    }

    private Token getMin() {
        stringToken = "min";
        return currentToken = Token.MIN;
    }

    private Token getMax() {
        stringToken = "max";
        return currentToken = Token.MAX;
    }

    private Token getL0() {
        stringToken = "l0";
        return currentToken = Token.L0;
    }

    private Token getT0() {
        stringToken = "l0";
        return currentToken = Token.T0;
    }

    public Token getNextToken() {
        parser.skipWhitespaces();
        if (parser.eof()) {
            return getEnd();
        }
        if (parser.between('x', 'z')) {
            return getVariable();
        }
        if (parser.between('0', '9')) {
            return getConst();
        }
        if (parser.take('(')) {
            return getBracket(true);
        }
        if (parser.take(')')) {
            return getBracket(false);
        }
        if (parser.take('+')) {
            return getPlus();
        }
        if (parser.take('-')) {
            return getMinus();
        }
        if (parser.take('*')) {
            return getMultiply();
        }
        if (parser.take('/')) {
            return getDivide();
        }
        if (parser.take("min")) {
            return getMin();
        }
        if (parser.take("max")) {
            return getMax();
        }
        if (parser.take("l0")) {
            return getL0();
        }
        if (parser.take("t0")) {
            return getT0();
        }
        throw new IllegalArgumentException("Unexpected token: '" + parser.next() + "'");
    }

    public String getStringToken() {
        return stringToken;
    }

    public void expect(Token expected) {
        if (expected != currentToken) {
            throw new IllegalStateException("Expected token: '" + expected + "', found token: '" + currentToken + "'\n" +
                    "\tExpression: '" + parser.getSource() + "'");
        }
    }

    public boolean checkForWhitespace() {
        return parser.check(Character::isWhitespace);
    }
}