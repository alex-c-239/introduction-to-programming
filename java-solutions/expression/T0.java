package expression;

public class T0 extends UnaryExpression implements Expression, TripleExpression {

    public T0(AnyExpression expression) {
        super(expression,"t0");
    }

    private int calculate(String binEx) {
        int ans = 0;
        int i;
        for (i = binEx.length() - 1; i >= 0; --i) {
            if (binEx.charAt(i) != '0') {
                break;
            }
            ans++;
        }
        if (i == -1) {
            ans += 32 - binEx.length();
        }
        return ans;
    }

    @Override
    public int evaluate(int x) {
        if (expression instanceof Expression) {
            return calculate(Integer.toBinaryString(((Expression) expression).evaluate(x)));
        }
        throw new UnsupportedOperationException("Unsupported Operation");
    }

    @Override
    public int evaluate(int x, int y, int z) {
        if (expression instanceof TripleExpression) {
            return calculate(Integer.toBinaryString(((TripleExpression) expression).evaluate(x, y, z)));
        }
        throw new UnsupportedOperationException("Unsupported Operation");
    }
}
