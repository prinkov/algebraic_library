package xyz.prinkov.algebraic;

import xyz.prinkov.algebraic.finite.FiniteField;

import java.util.Random;

public class GF2 extends FiniteField implements Cloneable {
    private boolean[] coef;
    private int N;
    public static final GF2 unit = new GF2("1");
    public static final GF2 zero = new GF2("0");
    public static final Zp modul = new Zp("2");
    private Random rnd;

    public GF2()
    {
        N = 0;
    }

    public GF2(String s)
    {
        Zp temp = new Zp(s);
        String tempString = temp.toString(2);
        N = tempString.length();
        setCoef(new boolean[N]);

        for (int i = 0; i < N; i++)
            if (tempString.charAt(i) == '0')
                getCoef()[i] = false;
            else
                getCoef()[i] = true;
    }

    public GF2(GF2 other)
    {
        N = other.N;
        setCoef(new boolean[N]);
        for (int i = 0; i < N; i++) {
            getCoef()[i] = other.getCoef()[i];
        }
    }

    public String toBinaryString(GF2 base)
    {
        int baseN = base.N;
        StringBuffer tempString = new StringBuffer();
        if(base != null)
            if(baseN > N)
                for(int i = 0; i < baseN  - N; i++)
                    tempString.append("0");
        for (int i = 0; i < N; i++)
            if (getCoef()[i])
                tempString.append("1");
            else
                tempString.append("0");
        return tempString.toString();
    }

    public String toString()
    {
        StringBuffer tempString = new StringBuffer(toBinaryString(new GF2("65539")));
        Zp forReturn = new Zp(tempString.toString(), 2);
        return forReturn.toString();
    }

    @Override
    public String random(int diapason, int min) {
        return null;
    }

    public int compareTo(GF2 val)
    {
        Zp a = new Zp(this.toString());
        Zp b = new Zp(val.toString());
        return a.compareTo(b);
    }

    public GF2 getBase(int n)
    {
        GF2 forReturn = new GF2("2");

        // auto generated
        GF2 exponent = new GF2(((Zp)modul.pow(new Zp(Integer.toString(n)), modul.pow(n+1))).add(new Zp("-1"), null).toString());
        Zp limit = new Zp(modul.pow(new Zp(Integer.toString(n+1)), modul.pow(n+2)).toString());
        Zp i =  new Zp(modul.pow(new Zp(Integer.toString(n)), modul.pow(n+1)).toString());
        while(i.compareTo(limit) != 0)
        {
            if(unit.compareTo((GF2)forReturn.pow(new GF2(exponent.toString()), new GF2(i.toString()))) == 0)
            {
                forReturn = new GF2(i.toString());
                break;
            }
            i = (Zp) i.add(Zp.unit, null);
        }
        return forReturn;
    }

    public Object add(Object t1, Object t2)
    {
        GF2 a = (GF2) t1;
        GF2 forReturn = new GF2();
        forReturn.setN(maxN(this, a));
        a.setN(maxN(this, a));
        this.setN(maxN(this, a));

        for (int i = 0; i < N; i++)
            forReturn.getCoef()[i] = this.getCoef()[i] ^ a.getCoef()[i];

        return (Object) forReturn;

    }

    public Object multiply(Object t1, Object t2)
    {
        GF2 a = new GF2((GF2) t1);
        GF2 p = new GF2((GF2) t2);
        GF2 forReturn = new GF2(zero);

        if (a.isUnit())
            return this;
        else if (this.isUnit())
            return a;

        boolean flag = p.getCoef()[0];

        if (flag)
            p.getCoef()[0] = false;

        p.nDecreese();
        a.setN(p.N);
        this.setN(p.N);
        forReturn.setN(p.N);

        for (int i = N - 1; i >= 0; i--)
        {
            if (a.getCoef()[0] && i != N - 1)
            {
                a = a.shiftLeft();
                a = (GF2) a.add(p, p);
            } else if (i != N - 1) {
                a = a.shiftLeft();
            }
            if (getCoef()[i]) {
                forReturn = (GF2) forReturn.add(a, a);
            }
        }
        p.setN(p.N + 1);
        p.getCoef()[0] = flag;

        return forReturn;
    }

    private GF2 shiftLeft()
    {
        GF2 forReturn = new GF2(this);
        for (int i = 0; i < forReturn.N - 1; i++)
            forReturn.getCoef()[i] = forReturn.getCoef()[i + 1];
        forReturn.getCoef()[forReturn.N - 1] = false;

        return forReturn;
    }

    public String random()
    {
        rnd = new Random();
        long forReturn = rnd
                .nextInt(Math.abs(((int) (Math.pow(2, this.N - 1)) / 2)));
        return Long.toString(forReturn);
    }

    public Object pow(Object t1, Object t2)
    {
        GF2 exponent = (GF2)t1;
        GF2 forReturn = (GF2)this;
        for (int i = 1; i < exponent.N; i++)
        {
            forReturn = (GF2)forReturn.multiply(forReturn, t2);
            if(exponent.getCoef()[i] == true)
                forReturn = (GF2)forReturn.multiply(this, t2);
        }
        return (Object) forReturn;
    }


    public void set(String s)
    {
        Zp temp = new Zp(s);
        String tempString = temp.toString(2);
        N = tempString.length();
        setCoef(new boolean[N]);

        for (int i = 0; i < N; i++)
            if (tempString.charAt(i) == '0')
                getCoef()[i] = false;
            else
                getCoef()[i] = true;
    }

    private boolean isUnit()
    {
        if (this.N == 1)
            if (this.getCoef()[0])
                return true;
        return false;

    }

    private void nDecreese()
    {
        for (int i = 0; i < N - 1; i++) {
            getCoef()[i] = getCoef()[i + 1];
        }
        getCoef()[N - 1] = false;
        N = N - 1;
    }

    private void setN(int newN)
    {
        if (newN == N)
            return;
        boolean[] newCoef = new boolean[newN];
        if (newN > N) {
            int i = newN - 1;
            for (int j = N - 1; j >= 0; j--)
                newCoef[i--] = getCoef()[j];

            for (int j = 0; j < newN - N; j++)
                newCoef[j] = false;
            N = newN;
            setCoef(new boolean[newN]);
            for (int j = 0; j < N; j++)
                getCoef()[j] = newCoef[j];
        }

    }

    public GF2 getGenerator()
    {
        return new GF2("2");
    }

    private int maxN(GF2 a, GF2 b)
    {
        if (a.N > b.N)
            return a.N;
        else
            return b.N;
    }

    @Override
    public Object getZero()
    {
        return new GF2("0");
    }

    @Override
    public Object getAddInverse(Object t1)
    {
        GF2 forReturn = new GF2();
        GF2 a = new GF2((GF2) t1);
        GF2 exponent = new GF2(((Zp)modul.pow(new Zp(Integer.toString(a.N - 1)), modul.pow(a.N+1))).add(new Zp("-1"), null).toString());
        forReturn = (GF2) this.add(((Object) new GF2(exponent.toString())), a);
        return (Object) forReturn;
    }

    @Override
    public Object getMultiplyInverse(Object t1, Object t2, Object secretOption)
    {
        GF2 forReturn = (GF2) t1;
        forReturn = (GF2)forReturn.pow(((GF2)secretOption).getAddInverse(((GF2)t2)), ((GF2)t2));
        return forReturn;
    }

    public int getN()
    {
        return N;
    }

    @Override
    public Object unit()
    {
        return new GF2("1");
    }

    public void setCoef(boolean[] coef)
    {
        this.coef = coef;
    }

    public boolean[] getCoef()
    {
        return coef;
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