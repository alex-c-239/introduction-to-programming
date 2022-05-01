package expression;

import java.math.BigInteger;

public class Add extends BinaryExpression implements Expression, TripleExpression, BigIntegerExpression {

    public Add(AnyExpression ex1, AnyExpression ex2) {
        super(ex1, ex2, "+");
    }

    @Override
    public int getPriority() {
        return 2;
    }

    @Override
    public boolean isAssociativeWith(AnyExpression expression) {
        return expression instanceof Add || expression instanceof Subtract;
    }

    @Override
    public BigInteger evaluate(BigInteger x) {
        if (ex1 instanceof BigIntegerExpression && ex2 instanceof BigIntegerExpression) {
            return ((BigIntegerExpression) ex1).evaluate(x).add(((BigIntegerExpression) ex2).evaluate(x));
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int evaluate(int x) {
        if (ex1 instanceof Expression && ex2 instanceof Expression) {
            return ((Expression) ex1).evaluate(x) + ((Expression) ex2).evaluate(x);
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (ex1 instanceof TripleExpression && ex2 instanceof TripleExpression) {
            return ((TripleExpression) ex1).evaluate(x, y, z) + ((TripleExpression) ex2).evaluate(x, y, z);
        }
        throw new UnsupportedOperationException("Unsupported operation");
    }
}