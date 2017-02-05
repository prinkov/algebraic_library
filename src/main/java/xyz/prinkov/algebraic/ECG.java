package xyz.prinkov.algebraic;

import xyz.prinkov.algebraic.finite.FiniteMultGroup;

import java.util.Random;

public class ECG implements FiniteMultGroup {
    private static int a;
    private static int b;

    private static Zp modul;
    private static Zp points[][];
    public Zp[] point;
    private String num;
    public static int length = 0; // cardinals

    public static void initECG(int a_, int b_, Zp modul_) {
        int u = 0;
        a = a_;
        b = b_;
        modul = modul_;
        int mod = Integer.parseInt(modul.toString());
        Zp[][] tempPoints = new Zp[mod * mod][2];
        Zp t[];
        for (int i = 0; i < mod; i++) {
            t = formula(a, b, new Zp(Integer.toString(i)), modul);
            if (t != null) {
                tempPoints[u][0] = new Zp(Integer.toString(i));
                tempPoints[u][1] = t[0];
                u++;
                tempPoints[u][0] = new Zp(Integer.toString(i));
                tempPoints[u][1] = t[1];
                u++;
            }
        }
        points = new Zp[u][2];
        for (int i = 0; i < u; i++) {
            points[i][0] = tempPoints[i][0];
            points[i][1] = tempPoints[i][1];
        }
        length = u;
    }

    private static Zp[] formula(int a, int b, Zp x, Zp mod) {
        Zp forReturn = new Zp("0");
        forReturn = (Zp) x.pow(new Zp("3"), mod);
        forReturn = (Zp) forReturn.add((Zp) x.multiply(new Zp(Integer.toString(a)),
                mod), null);
        forReturn = (Zp) forReturn.add(new Zp(Integer.toString(b)), mod);
        return forReturn.sqrt(mod);
    }

    public ECG(String s) {
        if (s.compareTo("u") == 0) {
            num = "u";
            return;
        }
        num = s;
        int ind = Integer.parseInt(s);
        point = new Zp[2];
        point[0] = points[ind][0];
        point[1] = points[ind][1];
    }

    public ECG(ECG other) {
        num = other.num;
        point = new Zp[2];
        point[0] = other.point[0];
        point[1] = other.point[1];
    }

    @Override
    public void set(String s) {
        point = points[Integer.parseInt(s)];
        num = s;
    }

    @Override
    public String random() {
        Random rnd = new Random();
        int r = rnd.nextInt(length);
        return Integer.toString(r);
    }

    @Override
    public Object multiply(Object t1, Object t2) {
        ECG c1 = (ECG) this;
        ECG c2 = (ECG) t1;

        // System.out.print("("+c1.point[0] + "," + c1.point[1]+") * ");
        // System.out.print("("+c2.point[0] + "," + c2.point[1]+") = ");

        if (c1.point[0].compareTo(c2.point[0]) == 0)
            if (c1.point[1].compareTo(c2.point[1].negate().mod(modul)) == 0) {
                return new ECG("u");
            }
        Zp lambda = new Zp();
        if (c1.compareTo(c2) != 0) {
            lambda = (Zp) c2.point[1].add(c1.point[1].negate(), modul);
            lambda = (Zp) lambda.multiply(((Zp)((c2.point[0].add(c1.point[0]
                    .negate(), modul)))).modInverse(modul), modul);
        } else {
            lambda = (Zp) c1.point[0].pow(new Zp("2"), modul);
            lambda = (Zp) ((Zp) lambda.multiply(new Zp("3"), modul)).add(
                    new Zp(Integer.toString(a)), modul);
            lambda = (Zp) lambda.multiply(new Zp("2").modInverse(modul), modul);
            lambda = (Zp) lambda.multiply(c1.point[1].modInverse(modul), modul);
        }
        ECG forReturn = new ECG("0");
        forReturn.point[0] = (Zp) ((Zp)((Zp) lambda.pow(new Zp("2"), modul))
                .add(c1.point[0].negate(), null)).add(c2.point[0].negate(), modul);
        lambda = (Zp) lambda.multiply(
                c1.point[0].add(forReturn.point[0].negate(), null), modul);
        forReturn.point[1] = ((Zp)(c1.point[1].negate()).add(lambda, null)).mod(modul);
        // System.out.println("("+forReturn.point[0] + "," + forReturn.point[1]
        // + ")");
        forReturn.changeNum();
        return forReturn;
    }

    @Override
    public Object pow(Object t1, Object t2) {
        // TODO Auto-generated method stub
        return null;
    }

    private void changeNum()
    {
        for (int i = 0; i < length; i++) {
            if (point[0].compareTo(points[i][0]) == 0)
                if (point[1].compareTo(points[i][1]) == 0) {
                    num = Integer.toString(i);
                }
        }
    }

    @Override
    public Object unit() {
        return new ECG("u");
    }

    public boolean isUnit() {
        //
        return true;
    }

    public int compareTo(ECG other) {
        if (point[0].compareTo(other.point[0]) == 0)
            if (point[1].compareTo(other.point[1]) == 0)
                return 0;
        return -1;
    }

    @Override
    public Object getMultiplyInverse(Object t1, Object t2, Object secretOption) {
        return ((ECG) t1).negate();
    }

    public String toStringDot() {
        StringBuffer s = new StringBuffer();
        if (num.compareTo("u") == 0)
            s.append("(oo; oo)");
        else
            s.append("(" + point[0].toString() + ", " + point[1].toString()
                    + ")");
        return s.toString();
    }

    public String toString() {
        return num;
    }

    public ECG negate() {
        ECG forReturn = new ECG(this);
        point[1] = point[1].negate();
        return forReturn;
    }

    @Override
    public Object sqrt(Object mod) {
        // TODO Auto-generated method stub
        return null;
    }
}