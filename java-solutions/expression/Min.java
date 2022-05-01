package expression;

import java.math.BigInteger;

public class Min extends BinaryExpression implements Expression, TripleExpression, BigIntegerExpression {

    public Min(AnyExpression ex1, AnyExpression ex2) {
        super(ex1, ex2, "min");
    }

    @Override
    public int getPriority() {
        return 1;
    }

    @Override
    public boolean isAssociativeWith(AnyExpression expression) {
        return expression instanceof Min || expression instanceof Max;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        if (ex1 instanceof BigIntegerExpression && ex2 instanceof BigIntegerExpression) {
            return ((BigIntegerExpression) ex1).evaluate(x).min(((BigIntegerExpression) ex2).evaluate(x));
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int evaluate(int x) {
        if (ex1 instanceof Expression && ex2 instanceof Expression) {
            return Math.min(((Expression) ex1).evaluate(x), ((Expression) ex2).evaluate(x));
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (ex1 instanceof TripleExpression && ex2 instanceof TripleExpression) {
            return Math.min(((TripleExpression) ex1).evaluate(x, y, z), ((TripleExpression) ex2).evaluate(x, y, z));
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }
}
