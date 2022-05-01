package expression;

public class L0 extends UnaryExpression implements Expression, TripleExpression {

    public L0(AnyExpression expression) {
        super(expression, "l0");
    }

    private int calculate(String binEx) {
        int ans = 32 - binEx.length();
        for (int i = 0; i < binEx.length(); ++i) {
            if (binEx.charAt(i) != '0') {
                break;
            }
            ans++;
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
