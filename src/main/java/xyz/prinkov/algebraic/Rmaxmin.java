package xyz.prinkov.algebraic;

import xyz.prinkov.algebraic.finite.FiniteSemiRing;

public class Rmaxmin extends FiniteSemiRing
{
    private double value;
    private static final double ZERO = Double.NEGATIVE_INFINITY;
    private static final double UNIT = Double.POSITIVE_INFINITY;

    public Rmaxmin()
    {
        this.value = Rmaxmin.ZERO;
    }

    public Rmaxmin(double value_)
    {
        this.value = value_;
    }

    public Rmaxmin(Rmaxmin other)
    {
        this.value = other.value;
    }

    public Rmaxmin pow(double d)
    {
        return this;
    }

    public Rmaxmin pow(Rmaxmin other)
    {
        return new Rmaxmin(value * other.value);
    }

    public boolean compareTo(Rmaxmin other)
    {
        return (value == other.value);
    }

    public Rmaxmin getZero()
    {
        return new Rmaxmin(ZERO);
    }

    public Rmaxmin unit()
    {
        return new Rmaxmin(UNIT);
    }

    public String toString()
    {
        if(value == Double.POSITIVE_INFINITY)
            return "+oo";
        else
        if(value == Double.NEGATIVE_INFINITY)
            return "-oo";
        return ""+value;
    }

    private double min(double a, double b)
    {
        if(a < b)
            return a;
        else
            return b;
    }

    private double max(double a, double b)
    {
        if(a > b)
            return a;
        else
            return b;
    }

    @Override
    public Object add(Object other, Object t2) {
        return new Rmaxmin(max(this.value, ((Rmaxmin)other).value));
    }

    @Override
    public Object multiply(Object other, Object t2) {
        return new Rmaxmin(min(this.value, (double)((Rmaxmin)other).value));
    }


    public int compareTo(Object other) {
        if(this.value == (double)((Rmaxmin)other).value)
            return 0;
        else if(this.value > (double)((Rmaxmin)other).value)
            return 1;
        else return -1;
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
    public Object pow(Object t1, Object t2) {
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