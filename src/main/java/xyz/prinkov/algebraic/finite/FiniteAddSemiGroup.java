package xyz.prinkov.algebraic.finite;

public interface FiniteAddSemiGroup {
	public abstract Object add(Object summand, Object module);
	public abstract void   set(String val);
	public abstract String random();
	public abstract Object multByScolar(int n);
	public abstract Object getZero();
}