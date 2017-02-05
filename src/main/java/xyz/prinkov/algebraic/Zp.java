package xyz.prinkov.algebraic;

import xyz.prinkov.algebraic.finite.FiniteField;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class Zp extends FiniteField implements Cloneable {
    public BigInteger number;
    private Random rnd;
    public static final Zp unit = new Zp("1");
    public static final Zp zero = new Zp("0");

    protected Object clone() {
        return new Zp(number);
    }

    public Zp(String val) {
        number = new BigInteger(val);
    }


    public Zp(BigInteger val) {
        number = val;
    }

    public Zp() {
        number = new BigInteger("0");
    }

    /**
     *
     * @param radix - система счисления
     * @param  - число
     */
    public Zp(String val, int radix) {
        number = new BigInteger(val, radix);
    }

    /**
     *
     * @param module - модуль
     * @return обратный в классе вычетов по модулю modul
     */
    public Zp modInverse(Zp module) {
        return new Zp(number.modInverse(module.number));
    }

    public Zp getBase(int n) {
        rnd = new SecureRandom();
        return (new Zp(BigInteger.probablePrime(n, rnd)));
    }

    public Object pow(Object t1, Object t2) {
        Zp exponent = (Zp) t1;
        Zp m = (Zp) t2;
        return (Object) (new Zp(number.modPow(exponent.number, m.number)));
    }

    public void set(String val) {
        number = new BigInteger(val);
    }

    public int compareTo(Zp val) {
        return number.compareTo(val.number);
    }

    public Object multiply(Object val, Object module) {
        Zp val1 = (Zp) val;
        BigInteger p = new BigInteger(((Zp) module).toString());
        return new Zp(number.multiply(val1.number).mod(p));
    }

    public Zp mod(Zp module) {
        return new Zp(number.mod(module.number));
    }

    public Zp negate() {
        return new Zp(number.negate());
    }

    public Object add(Object summand, Object module) {
        if(module == null) {
            return number.add(((Zp)summand).number);
        }
        Zp val = (Zp) summand;
        BigInteger p = ((Zp) module).number;
        return (Object) new Zp(number.add(val.number).mod(p));
    }

    public String toString() {
        return number.toString();
    }

    @Override
    public String random() {
        return null;
    }

    public String random(int diapason, int min) {
        rnd = new Random();
        int forReturn = rnd.nextInt(diapason) + min;
        return Integer.toString(forReturn);

    }

    public Zp getGenerator() {
        rnd = new Random();
        BigInteger g = new BigInteger(Integer.toString(rnd.nextInt(100) + 23));
        while (g.modPow(number.add(new BigInteger("-1")), number).compareTo(
                new BigInteger("1")) != 0)
            g = g.add(new BigInteger("1"));
        return new Zp(g);
    }

    @Override
    public Object getZero() {
        return new Zp("0");
    }

    @Override
    public Object getAddInverse(Object module) {
        Zp p = (Zp) module;
        return new Zp(p.number.add(new BigInteger("-1")).add(
                number.negate()));
    }

    @Override
    public Object getMultiplyInverse(Object t1, Object t2, Object secretOption) {
        Zp forReturn = (Zp) t1;
        forReturn = (Zp) forReturn.pow(
                ((Zp) secretOption).getAddInverse(t2), t2);
        return forReturn;
    }

    @Override
    public Object unit() {
        return new Zp("1");
    }

    public String toString(int i) {
        return number.toString(i);
    }

    public Object pow(int n) {
        return new Zp(number.pow(n));
    }

    @Override
    public Object multByScolar(int scolar) {
        return number.multiply(new BigInteger(Integer.toString(scolar)));
    }

    public Zp[][] getQuadraticResidues() {
        int mod = Integer.parseInt(this.toString());
        Zp[][] ret = new Zp[(mod - 1) / 2][2];
        Zp temp = new Zp("1");
        for (int i = 0; i < Integer.parseInt(this.toString()); i++) {
            temp.set(Integer.toString(i));
        }
        return ret;
    }

    public int symbolLegendre(Object modul) {
        Zp mod = (Zp) modul;
        Zp forReturn = (Zp) this;
        int exponent = Integer.parseInt(mod.toString());
        int temp = exponent;
        exponent = (exponent - 1) / 2;
        int symbolLegendre = Integer.parseInt(((Zp) forReturn.pow(exponent))
                .mod(mod).toString());
        if ((temp - 1) == symbolLegendre)
            symbolLegendre = -1;
        return symbolLegendre;
    }

    @Override
    public Zp[] sqrt(Object mod) {
        Zp i = new Zp("0");
        int u = 0;
        Zp[] forReturn = new Zp[2];
        for (; i.compareTo((Zp) mod) == -1 ; i = (Zp) i.add(Zp.unit, null)) {
            if (((Zp) i.pow(2)).mod((Zp) mod).compareTo(this.mod((Zp) mod)) == 0) {
                forReturn[u] = i;
                u++;
            }
        }
        if (this.symbolLegendre((Zp) mod) == 1)
            return forReturn;
        else
            return null;
    }
}