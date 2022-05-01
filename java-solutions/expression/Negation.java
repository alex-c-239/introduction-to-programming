package expression;

import java.math.BigInteger;

public class Negation extends UnaryExpression implements Expression, TripleExpression, BigIntegerExpression {

    public Negation(AnyExpression expression) {
        super(expression, "-");
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        if (expression instanceof BigIntegerExpression) {
            return ((BigIntegerExpression) expression).evaluate(x).negate();
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int evaluate(int x) {
        if (expression instanceof Expression) {
            return -((Expression) expression).evaluate(x);
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (expression instanceof TripleExpression) {
            return -((TripleExpression) expression).evaluate(x, y, z);
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
