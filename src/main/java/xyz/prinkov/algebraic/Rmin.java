package xyz.prinkov.algebraic;

import xyz.prinkov.algebraic.finite.FiniteSemiField;

public class Rmin extends FiniteSemiField
{
    private double value;
    private static final double ZERO = Double.POSITIVE_INFINITY;
    private static final double UNIT = 0;

    public Rmin()
    {
        this.value = Rmin.ZERO;
    }

    public Rmin(double value_)
    {
        this.value = value_;
    }

    public Rmin(Rmin other)
    {
        this.value = other.value;
    }

    public Rmin divide(Rmin other)
    {
        return new Rmin(this.value - other.value);
    }

    public Rmin pow(double d)
    {
        return new Rmin(d * value);
    }

    public Rmin pow(Rmin other)
    {
        return new Rmin(value * other.value);
    }

    public Rmin getZero()
    {
        return new Rmin(ZERO);
    }

    public Rmin unit()
    {
        return new Rmin(UNIT);
    }

    public String toString()
    {
        if(value == Double.POSITIVE_INFINITY)
            return "+oo";

        return ""+value;
    }

    private double min(double a, double b)
    {
        if(a < b)
            return a;
        else
            return b;
    }

    @Override
    public Object add(Object other, Object t2) {
        return new Rmin(min(this.value, (double)((Rmin)other).value));
    }

    @Override
    public Object multiply(Object other, Object t2) {
        return new Rmin(this.value + (double)((Rmin)other).value);
    }


    public int compareTo(Object other) {
        if(value == (Double)((Rmin)other).value)
            return 0;
        else if(value > (Double)((Rmin)other).value)
            return 1;
        else return -1;
    }


    public void set(Object t) {
        value = (double)((Rmin)t).value;

    }

    @Override
    public  Object getMultiplyInverse(Object t1, Object t2, Object t3)
    {
        Rmin forReturn = new Rmin(-this.value);
        return (Object) forReturn;
    }

    public static FiniteMatrix<Rmin> inverse( FiniteMatrix<Rmin> FiniteMatrix)
    {
        int cols = FiniteMatrix.getCols();
        int rows = FiniteMatrix.getRows();
        FiniteMatrix<Rmin> forReturn = new FiniteMatrix<Rmin>(cols, rows, FiniteMatrix.get(0, 0));
        for(int i = 0; i < FiniteMatrix.getRows(); i++)
            for(int j = 0; j < cols; j++)
                if(FiniteMatrix.get(j, i).compareTo(FiniteMatrix.get(0, 0).getZero()) == 0)
                    forReturn.set(i, j, (Rmin) FiniteMatrix.get(0, 0).getZero());
                else
                    forReturn.set(i, j, (Rmin) ((Rmin) FiniteMatrix.get(j, i)).getMultiplyInverse(null, null, null));
        return forReturn;
    }

    public static FiniteMatrix<Rmin> firstSolution(FiniteMatrix<Rmin> a, FiniteMatrix<Rmin> b)
    {
        FiniteMatrix<Rmin> x = new FiniteMatrix<Rmin>(inverse(b).multiply(Rmin.inverse(a)));
        return x;
    }

    public static FiniteMatrix<Rmin> secondSolution(FiniteMatrix<Rmin> a, FiniteMatrix<Rmin> b)
    {
        return (a.star(a.getRows())).multiply(b);
    }



    @Override
    public Object pow(Object t1, Object t2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void set(String s) {
        // TODO Auto-generated method stub

    }

    @Override
    public String random() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object multByScolar(int n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object sqrt(Object mod) {
        // TODO Auto-generated method stub
        return null;
    }
}