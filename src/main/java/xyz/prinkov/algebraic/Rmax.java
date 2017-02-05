package xyz.prinkov.algebraic;


import xyz.prinkov.algebraic.finite.FiniteSemiField;

public class Rmax extends FiniteSemiField
{

    private double value;
    private static final double ZERO = Double.NEGATIVE_INFINITY;
    private static final double UNIT = 0;

    public Rmax()
    {
        this.value = Rmax.ZERO;
    }

    public Rmax(double value_)
    {
        this.value = value_;
    }

    public Rmax(String s)
    {
        this.value = Double.parseDouble(s);
    }

    public Rmax(Rmax other)
    {
        this.value = other.value;
    }

    public static Object getInstance()
    {
        return  (Object) new Rmax("12");
    }

    public Rmax star(int n)
    {
        Rmax forReturn = new Rmax(ZERO);
        for(int i = 0; i < n; i++ ){
            forReturn = (Rmax)forReturn.add((Object)this.pow(i), null);
        }
        return forReturn;
    }

    public Rmax multiply(Rmax other)
    {
        return new Rmax(this.value + other.value);
    }

    public Rmax divide(Rmax other)
    {
        return new Rmax(this.value - other.value);
    }

    public Rmax pow(int d)
    {
        return new Rmax(d * value);
    }



    public Rmax pow(Rmax other)
    {
        return new Rmax(value * other.value);
    }

    public int compareTo(Object t)
    {
        Rmax other = (Rmax) t;
        if(value > other.value)
            return 1;
        else if(value > other.value)
            return -1;
        return 0;
    }
    @Override
    public Object getZero()
    {
        return (Object)new Rmax(ZERO);
    }
    @Override
    public Object unit()
    {
        return (Object) new Rmax(UNIT);
    }

    public String toString()
    {
        if(value == Double.NEGATIVE_INFINITY)
            return "-oo";
        return value + "";
    }

    private double max(double a, double b)
    {
        if(a > b)
            return a;
        else
            return b;
    }

    public void set(String s)
    {
        value = Double.parseDouble(s);
    }

    @Override
    public Object add(Object t1, Object t2)
    {
        Rmax other = (Rmax) t1;
        return new Rmax(max(this.value, other.value));
    }

    public void set(Object s) {
        this.value = (double)((Rmax) s).value;
    }

    @Override
    public Object multiply(Object t1, Object t2) {
        Rmax other = (Rmax) t1;
        return (Object)new Rmax(this.value + other.value);
    }

    @Override
    public  Object getMultiplyInverse(Object t1, Object t2, Object t3)
    {
        Rmax forReturn = new Rmax(-this.value);
        return (Object) forReturn;
    }

    public static FiniteMatrix<Rmax> inverse( FiniteMatrix<Rmax> FiniteMatrix)
    {
        int cols = FiniteMatrix.getCols();
        int rows = FiniteMatrix.getRows();
        FiniteMatrix<Rmax> forReturn = new FiniteMatrix<Rmax>(cols, rows, FiniteMatrix.get(0, 0));
        for(int i = 0; i < FiniteMatrix.getRows(); i++)
            for(int j = 0; j < cols; j++)
                if(FiniteMatrix.get(j, i).compareTo(FiniteMatrix.get(0, 0).getZero()) == 0)
                    forReturn.set(i, j, (Rmax) FiniteMatrix.get(0, 0).getZero());
                else
                    forReturn.set(i, j, (Rmax) ((Rmax) FiniteMatrix.get(j, i)).getMultiplyInverse(null, null, null));
        return forReturn;
    }

    public static FiniteMatrix<Rmax> firstSolution(FiniteMatrix<Rmax> a, FiniteMatrix<Rmax> b)
    {
        FiniteMatrix<Rmax> x = new FiniteMatrix<Rmax>(inverse(b).multiply(Rmax.inverse(a)));
        return x;
    }

    public static FiniteMatrix<Rmax> secondSolution(FiniteMatrix<Rmax> a, FiniteMatrix<Rmax> b)
    {
        return (a.star(a.getRows())).multiply(b);
    }

    @Override
    public String random() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object multByScolar(int n) {
        return null;
    }

    @Override
    public Object pow(Object t1, Object t2) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object sqrt(Object mod) {
        // TODO Auto-generated method stub
        return null;
    }

}