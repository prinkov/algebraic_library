package xyz.prinkov.algebraic.finite;

public abstract class FiniteField
        implements FiniteAddGroup, FiniteMultGroup {
    public abstract Object pow(Object multiplier, Object module);
    public abstract void   set(String val);
    public abstract String toString();
    public abstract String random(int diapason, int min);
}