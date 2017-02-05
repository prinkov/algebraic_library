package xyz.prinkov.algebraic.finite;

public interface FiniteMultSemiGroup {
    public abstract Object multiply(Object t1, Object t2);
    public abstract Object pow(Object t1, Object t2);
    public abstract void   set(String s);
    public abstract Object sqrt(Object mod);
    public abstract String random();
    public abstract Object unit();
}