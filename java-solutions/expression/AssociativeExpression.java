package expression;

public interface AssociativeExpression extends AnyExpression {
    default boolean isAssociativeWith(AnyExpression expression) {
        return false;
    }
}
