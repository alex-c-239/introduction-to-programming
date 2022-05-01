package expression;

import java.math.BigInteger;

public class Max extends BinaryExpression implements Expression, BigIntegerExpression, TripleExpression {

    public Max(AnyExpression ex1, AnyExpression ex2) {
        super(ex1, ex2, "max");
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean isAssociativeWith(AnyExpression expression) {
        return expression instanceof Max || expression instanceof Min;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        if (ex1 instanceof BigIntegerExpression && ex2 instanceof BigIntegerExpression) {
            return ((BigIntegerExpression) ex1).evaluate(x).max(((BigIntegerExpression) ex2).evaluate(x));
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int evaluate(int x) {
        if (ex1 instanceof Expression && ex2 instanceof Expression) {
            return Math.max(((Expression) ex1).evaluate(x), ((Expression) ex2).evaluate(x));
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (ex1 instanceof TripleExpression && ex2 instanceof TripleExpression) {
            return Math.max(((TripleExpression) ex1).evaluate(x, y, z), ((TripleExpression) ex2).evaluate(x, y, z));
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
