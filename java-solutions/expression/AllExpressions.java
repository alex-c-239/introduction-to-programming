package expression;

public abstract class AllExpressions implements AnyExpression {
    protected final String exType;

    public AllExpressions(String exType) {
        this.exType = exType;
    }

    public int getPriority() {
        return switch (exType) {
            case "min", "max" -> 1;
            case "+", "-" -> 2;
            case "*", "/" -> 3;
            case "negation", "l0", "t0" -> 4;
            case "variable", "const" -> Integer.MAX_VALUE;
            default -> Integer.MIN_VALUE;
        };
    }
}
