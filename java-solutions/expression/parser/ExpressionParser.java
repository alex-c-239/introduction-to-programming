package expression.parser;

import expression.*;

import static expression.AnyExpression.PRIORITY_MAX_VALUE;
import static expression.AnyExpression.PRIORITY_MIN_VALUE;

public class ExpressionParser implements Parser {
    private TokenSource source;
    private Token currentToken;

    private void getNextToken() {
        currentToken = source.getNextToken();
    }

    private int getPriority(Token token) {
        return switch (token) {
            case MIN, MAX -> 1;
            case PLUS, MINUS -> 2;
            case DIVIDE, MULTIPLY -> 3;
            case L0, T0 -> 4;
            default -> throw new IllegalArgumentException("Unexpected value: '" + token + "'");
        };
    }

    private void expect(Token expected) {
        source.expect(expected);
        getNextToken();
    }

    private TripleExpression parseVariable() {
        TripleExpression ans = new Variable(source.getStringToken());
        getNextToken();
        return ans;
    }

    private TripleExpression parseConst(boolean isPositive) {
        TripleExpression ans = new Const(Integer.parseInt(((!isPositive) ? "-" : "") + source.getStringToken()));
        getNextToken();
        return ans;
    }

    private TripleExpression parseL0() {
        expect(Token.L0);
        return new L0((AnyExpression) parseUnaryExpression());
    }

    private TripleExpression parseT0() {
        expect(Token.T0);
        return new T0((AnyExpression) parseUnaryExpression());
    }

    private TripleExpression parseNegation() {
        if (!source.checkForWhitespace()) {
            expect(Token.MINUS);
            if (currentToken == Token.CONST) {
                return parseConst(false);
            }
            return new Negation((AnyExpression) parseUnaryExpression());
        }
        expect(Token.MINUS);
        return new Negation((AnyExpression) parseUnaryExpression());
    }

    private TripleExpression parseBracketExpression() {
        expect(Token.BRACKET_OPEN);
        TripleExpression ans = parseByPriority(PRIORITY_MIN_VALUE);
        expect(Token.BRACKET_CLOSE);
        return ans;
    }

    private TripleExpression parseUnaryExpression() {
        return switch (currentToken) {
            case VARIABLE -> parseVariable();
            case CONST -> parseConst(true);
            case MINUS -> parseNegation();
            case L0 -> parseL0();
            case T0 -> parseT0();
            case BRACKET_OPEN -> parseBracketExpression();
            default -> throw new IllegalStateException("Unexpected token: '" + currentToken + "'");
        };
    }

    private TripleExpression parseMultiply(TripleExpression ex1) {
        expect(Token.MULTIPLY);
        return new Multiply((AnyExpression) ex1, (AnyExpression) parseByPriority(getPriority(Token.MULTIPLY) + 1));
    }

    private TripleExpression parseDivide(TripleExpression ex1) {
        expect(Token.DIVIDE);
        return new Divide((AnyExpression) ex1, (AnyExpression) parseByPriority(getPriority(Token.DIVIDE) + 1));
    }

    private TripleExpression parseAdd(TripleExpression ex1) {
        expect(Token.PLUS);
        return new Add((AnyExpression) ex1, (AnyExpression) parseByPriority(getPriority(Token.PLUS) + 1));
    }

    private TripleExpression parseSubtract(TripleExpression ex1) {
        expect(Token.MINUS);
        return new Subtract((AnyExpression) ex1, (AnyExpression) parseByPriority(getPriority(Token.MINUS) + 1));
    }

    private TripleExpression parseMax(TripleExpression ex1) {
        expect(Token.MAX);
        return new Max((AnyExpression) ex1, (AnyExpression) parseByPriority(getPriority(Token.MAX) + 1));
    }

    private TripleExpression parseMin(TripleExpression ex1) {
        expect(Token.MIN);
        return new Min((AnyExpression) ex1, (AnyExpression) parseByPriority(getPriority(Token.MIN) + 1));
    }

    private TripleExpression parseBinaryExpression(TripleExpression ex1) {
        return switch (currentToken) {
            case MIN -> parseMin(ex1);
            case MAX -> parseMax(ex1);
            case PLUS -> parseAdd(ex1);
            case MINUS -> parseSubtract(ex1);
            case MULTIPLY -> parseMultiply(ex1);
            case DIVIDE -> parseDivide(ex1);
            default -> throw new IllegalStateException("Unexpected value: '" + currentToken + "'");
        };
    }

    private boolean endOfExpression() {
        return currentToken == Token.END || currentToken == Token.BRACKET_CLOSE;
    }

    private TripleExpression parseByPriority(int priority) {
        if (priority == PRIORITY_MAX_VALUE) {
            return parseUnaryExpression();
        }
        TripleExpression ans = parseByPriority(priority + 1);
        while (!endOfExpression() && getPriority(currentToken) == priority) {
            ans = parseBinaryExpression(ans);
        }
        return ans;
    }

    @Override
    public TripleExpression parse(String expression) {
        source = new TokenSource(expression);
        getNextToken();
        return parseByPriority(PRIORITY_MIN_VALUE);
    }
}