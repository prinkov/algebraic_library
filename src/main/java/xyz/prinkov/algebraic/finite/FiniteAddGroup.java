package xyz.prinkov.algebraic.finite;

/**
 * Группа по сложению
 */
public interface FiniteAddGroup extends FiniteAddSemiGroup {
    /**
     *
     * @param module - модуль
     * @return возвращает обратный по сложению
     */
    public abstract Object getAddInverse(Object module);

}