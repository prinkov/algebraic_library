package xyz.prinkov.algebraic;

import xyz.prinkov.algebraic.finite.FiniteField;

import java.util.Random;

public class GF extends FiniteField
{
    private int[] coef;
    private int N;
    private Random rnd;
    public int modul;

    public GF()
    {
        modul = 0;
        N = 0;
    }

    public GF(int modul_)
    {
        modul = modul_;
    }

    public GF(GF other)
    {
        modul = other.modul;
        N = other.N;
        coef = new int[N];
        for (int i = 0; i < N; i++)
            coef[i] = other.coef[i];
    }

    public GF(String s, int modul_)
    {
        int temp = Integer.parseInt(s);
        int i = 0;
        modul = modul_;
        while ((int) Math.pow(modul, N) <= temp)
            N++;
        coef = new int[N];
        i = N - 1;
        while (temp != 0) {
            coef[i--] = temp % modul;
            temp /= modul;
        }
    }

    @Override
    public Object getAddInverse(Object t1)
    {
        GF forReturn = new GF("0", modul);
        this.changeCoeff();
        forReturn.setN(getN());
        for (int i = 0; i < N; i++)
            forReturn.coef[i] = modul - coef[i];
        return (Object) forReturn;
    }

    @Override
    public Object add(Object t1, Object t2)
    {
        GF a = (GF) t1;
        GF forReturn = new GF("0", modul);
        forReturn.setN(maxN(this, a));
        a.setN(maxN(this, a));
        this.setN(maxN(this, a));

        for (int i = 0; i < N; i++)
            forReturn.coef[i] = (this.coef[i] + a.coef[i]) % modul;

        return (Object) forReturn;
    }

    public GF minus(GF other)
    {
        GF forReturn = new GF("0", modul);

        other.setN(maxN(this, other));
        this.setN(maxN(this, other));
        forReturn.setN(N);

        for (int i = 0; i < N; i++) {
            forReturn.coef[i] = coef[i] - other.coef[i];
        }

        forReturn.changeCoeff();
        forReturn.removeZeros();
        return forReturn;
    }

    public GF divide(GF divisor)
    {
        GF forReturn = new GF("0", this.modul);
        // forReturn = a/b
        GF a = new GF(this);
        GF b = new GF(divisor);
        forReturn.setN(a.getN() - b.getN() + 1);
        int exp;
        GF temp;
        exp = a.getN() - b.getN();

        while (a.compareTo(b) != -1) {
            exp = a.getN() - b.getN();
            forReturn.coef[forReturn.getN() - 1 - exp] = a.coef[0];
            temp = new GF(Integer.toString(a.coef[0]
                    * (int) Math.pow(a.modul, exp)), a.modul);
            a = a.minus(temp.multiply(divisor));
        }
        return forReturn;
    }

    public GF mod(GF divisor)
    {
        GF forReturn = new GF("0", this.modul);

        // forReturn = a%b
        GF a = new GF(this);
        GF b = new GF(divisor);
        if (a.getN() < b.getN())
            return a;
        forReturn.setN(a.getN() - b.getN() + 1);
        int exp;
        GF temp;
        exp = a.getN() - b.getN();
        while (a.getN() >= b.getN() ) {
            exp = a.getN() - b.getN();
            forReturn.coef[forReturn.getN() - 1 - exp] = a.coef[0];
            temp = new GF(Integer.toString(a.coef[0]
                    * (int) Math.pow(a.modul, exp)), a.modul);
            a = a.minus(temp.multiply(divisor));
        }
        return a;
    }

    @Override
    public Object getZero()
    {
        return new GF("0", modul);
    }

    @Override
    public Object getMultiplyInverse(Object t1, Object t2, Object secretOption)
    {
        GF forReturn = (GF) t1;
        forReturn = (GF)forReturn.pow(((GF)secretOption).getAddInverse(((GF)t2)), ((GF)t2));
        return forReturn;
    }

    @Override
    public Object multiply(Object t1, Object t2)
    {
        GF a = (GF) this;
        GF b = (GF) t1;

        GF temp = new GF("0", a.modul);

        if (a.getN() < b.getN())
            a.setN(b.getN());
        if (a.getN() > b.getN())
            b.setN(a.getN());
        temp.setN(a.getN() + b.getN() - 1);

        for (int i = 0; i < a.getN(); i++)
            for (int j = 0; j < b.getN(); j++)
                temp.coef[i + j] = (temp.coef[i + j] + (a.coef[i] * b.coef[j])
                        % modul)
                        % modul;
        temp.removeZeros();
        GF forReturn = temp.mod((GF) t2);
        return forReturn;
    }

    public GF multiply(GF t1)
    {
        GF a = (GF) this;
        GF b = (GF) t1;

        GF temp = new GF("0", a.modul);

        if (a.getN() < b.getN())
            a.setN(b.getN());
        if (a.getN() > b.getN())
            b.setN(a.getN());
        temp.setN(a.getN() + b.getN() - 1);

        for (int i = 0; i < a.getN(); i++)
            for (int j = 0; j < b.getN(); j++)
                temp.coef[i + j] = (temp.coef[i + j] + (a.coef[i] * b.coef[j])
                        % modul)
                        % modul;

        return temp;
    }

    @Override
    public Object unit()
    {
        return new GF("1", modul);
    }

    @Override
    public Object pow(Object t1, Object t2)
    {
        GF2 exponent = new GF2(t1.toString());
        GF forReturn = (GF)this;

        for (int i = 1; i < exponent.getN(); i++)
        {
            forReturn = (GF)forReturn.multiply(forReturn, t2);
            if(exponent.getCoef()[i] == true)
                forReturn = (GF)forReturn.multiply(this, t2);
        }
        return (Object) forReturn;
    }

    @Override
    public void set(String s)
    {
        int temp = Integer.parseInt(s);
        int i = 0;
        while ((int) Math.pow(modul, N) <= temp)
            N++;
        coef = new int[N];
        i = N - 1;
        while (temp != 0) {
            coef[i--] = temp % modul;
            temp /= modul;
        }
    }

    @Override
    public String toString()
    {
        int temp = 0;
        for (int i = 0; i < N; i++)
            temp += coef[i] * (int) Math.pow(modul, N - i - 1);
        return Integer.toString(temp);
    }

    @Override
    public String random(int diapason, int min) {
        return null;
    }

    @Override
    public String random()
    {
        rnd = new Random();
        long forReturn = rnd
                .nextInt(Math.abs(((int) (Math.pow(modul, this.N - 1)) / 2)));
        return Long.toString(forReturn);
    }

    public int compareTo(GF other)
    {
        other.setN(maxN(this, other));
        this.setN(maxN(this, other));
        for (int i = 0; i < maxN(this, other); i++)
            if (coef[i] > other.coef[i]) {
                other.removeZeros();
                this.removeZeros();
                return 1;
            } else if (other.coef[i] > coef[i]) {
                other.removeZeros();
                this.removeZeros();
                return -1;
            }
        return 0;
    }

    private void setN(int newN)
    {
        if (newN == N)
            return;
        int[] newCoef = new int[newN];
        if (newN > N) {
            int i = newN - 1;
            for (int j = N - 1; j >= 0; j--)
                newCoef[i--] = coef[j];
            for (int j = 0; j < newN - N; j++)
                newCoef[j] = 0;
            N = newN;
            coef = new int[newN];
            for (int j = 0; j < N; j++)
                coef[j] = newCoef[j];
        }
    }

    public int getN()
    {
        return N;
    }

    private void removeZeros()
    {
        int pointer = 0;

        while (coef[pointer] == 0 && pointer < N - 1)
            pointer++;
        if (N - pointer < 1)
            return;
        if (pointer == 0)
            return;
        for (int i = 0; i < N - pointer; i++)
            coef[i] = coef[i + pointer];
        N = N - pointer;

    }

    private void changeCoeff()
    {
        for (int i = 0; i < N; i++)
            if (coef[i] < 0)
                coef[i] = modul + coef[i];
    }

    private int maxN(GF a, GF b)
    {
        if (a.N > b.N)
            return a.N;
        else
            return b.N;
    }

    public GF getGenerator()
    {
        return new GF("4", modul);
    }

    public GF getBase(int n)
    {
        return new GF("10", 3);
    }

    @Override
    public Object multByScolar(int n) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public GF[] sqrt(Object t1) {
        GF mod = (GF) t1;
        GF igf = new GF("1", modul);
        int u = 0;
        GF[] forReturn = new GF[2];
        int limit = (int)Math.pow(modul, N);
        for(int i = 0; i < limit; i++)
        {
            igf = new GF(Integer.toString(i), modul);
            if(((GF)igf.pow(new GF("2", modul), mod)).mod((GF)mod).compareTo(this.mod((GF)mod)) == 0)
            {
                forReturn[u] = igf;
                u++;
            }
        }
        return forReturn;
    }

    public GF negate() {
        // TODO Auto-generated method stub
        return null;
    }
}