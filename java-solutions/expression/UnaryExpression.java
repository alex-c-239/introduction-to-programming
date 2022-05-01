package expression;

import java.util.Objects;

public abstract class UnaryExpression implements AnyExpression {
    protected final AnyExpression expression;
    private final String operation;

    public UnaryExpression(AnyExpression expression, String operation) {
        this.expression = expression;
        this.operation = operation;
    }

    @Override
    public int getPriority() {
        return PRIORITY_MAX_VALUE;
    }

    @Override
    public int hashCode() {
        return Objects.hash(expression, this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof UnaryExpression) {
            return this.getClass() == obj.getClass() && expression.equals(((UnaryExpression) obj).expression);
        }
        return false;
    }

    @Override
    public String toString() {
        return operation + "(" + expression + ")";
    }

    @Override
    public String toMiniString() {
        return operation + (expression.getPriority() >= this.getPriority() ?
                " " + expression.toMiniString() :
                "(" + expression.toMiniString() + ")");
    }
}
