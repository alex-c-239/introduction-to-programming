package expression;

import java.util.Objects;

public abstract class BinaryExpression implements AssociativeExpression {
    protected final AnyExpression ex1;
    protected final AnyExpression ex2;
    private final String operation;

    public BinaryExpression(AnyExpression ex1, AnyExpression ex2, String operation) {
        this.ex1 = ex1;
        this.ex2 = ex2;
        this.operation = operation;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ex1, ex2, this.getClass());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BinaryExpression) {
            return this.getClass() == obj.getClass() &&
                    ex1.equals(((BinaryExpression) obj).ex1) &&
                    ex2.equals(((BinaryExpression) obj).ex2);
        }
        return false;
    }

    private String toMiniStringFirst() {
        return ex1.getPriority() < this.getPriority() ? "(" + ex1.toMiniString() + ")" : ex1.toMiniString();
    }

    private String toMiniStringSecond() {
        return ex2.getPriority() <= this.getPriority() && !this.isAssociativeWith(ex2) ?
                "(" + ex2.toMiniString() + ")" :
                ex2.toMiniString();
    }

    @Override
    public String toMiniString() {
        return toMiniStringFirst() + " " + operation + " " + toMiniStringSecond();
    }

    @Override
    public String toString() {
        return "(" + ex1 + " " + operation + " " + ex2 + ")";
    }
}