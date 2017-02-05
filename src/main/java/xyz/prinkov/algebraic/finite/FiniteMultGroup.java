package xyz.prinkov.algebraic.finite;

public interface FiniteMultGroup
        extends FiniteMultSemiGroup {
    public abstract Object getMultiplyInverse(Object t1, Object t2, Object secretOption);
}