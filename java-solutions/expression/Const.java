package expression;

import java.math.BigInteger;
import java.util.Objects;

public class Const implements AnyExpression, Expression, TripleExpression, BigIntegerExpression{
    private final Number value;

    public Const(int value) {
        this.value = value;
    }

    public Const(BigInteger value) {
        this.value = value;
    }

    @Override
    public int getPriority() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

    @Override
    public int evaluate(int x) {
        if (value instanceof Integer) {
            return (int) value;
        }
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        return evaluate(x);
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        if (value instanceof BigInteger) {
            return (BigInteger) value;
        }
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Const) {
            return ((Const) obj).value.equals(value);
        }
        return false;
    }

    @Override
    public String toString() {
        return value.toString();
    }
}