package expression;

import java.math.BigInteger;

public class Variable implements AnyExpression, Expression, TripleExpression, BigIntegerExpression {
    private final String name;

    public Variable(String name) {
        this.name = name;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int evaluate(int x) {
        return x;
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return switch (name) {
            case "x" -> x;
            case "y" -> y;
            case "z" -> z;
            default -> throw new IllegalStateException("Unexpected variable name: " + name);
        };
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        return x;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Variable) {
            return name.equals(((Variable) obj).name);
        }
        return false;
    }

    @Override
    public String toString() {
        return name;
    }
}
