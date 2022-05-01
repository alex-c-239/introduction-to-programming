package expression;

public interface AnyExpression extends ToMiniString{
    int getPriority();
    int PRIORITY_MIN_VALUE = 1;
    int PRIORITY_MAX_VALUE = 4;
}
