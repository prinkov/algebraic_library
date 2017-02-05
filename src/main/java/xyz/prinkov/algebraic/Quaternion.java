package xyz.prinkov.algebraic;

import java.io.IOException;

public class Quaternion
{
    private Zp[] coef;
    private Zp modul;

    public Quaternion() throws IOException
    {
        coef = new Zp[4];
        modul = new Zp("0");
    }

    public Quaternion(int a, int b, int c, int d, int modul_) throws IOException
    {
        coef = new Zp[4];
        coef[0] = new Zp(Integer.toString(a));
        coef[1] = new Zp(Integer.toString(b));
        coef[2] = new Zp(Integer.toString(c));
        coef[3] = new Zp(Integer.toString(d));

        modul = new Zp(Integer.toString(modul_));
        normCoeff();
    }

    public Quaternion multiply(int n) throws NumberFormatException, IOException
    {
        Quaternion forReturn = this.zero();
        for(int i = 0; i < 4; i++)
            forReturn.coef[i] = (Zp) coef[i].multiply(new Zp(Integer.toString(n)), modul);
        forReturn.normCoeff();
        return forReturn;
    }

    public Quaternion multiply(Quaternion other) throws IOException
    {
        Quaternion forReturn = new Quaternion();
        forReturn.modul = new Zp(modul.toString());
        for(int i = 0; i < 4; i++)
            forReturn.coef[i] = (Zp) coef[0].multiply(other.coef[i], modul);
        for(int i = 1; i < 4; i++)
            forReturn.coef[0] = (Zp) forReturn.coef[0].add(((Zp) coef[i].multiply(other.coef[i], modul)).negate(), modul);

        forReturn.coef[1] = (Zp) forReturn.coef[1].add(((Zp) coef[3].multiply(other.coef[2], modul)).negate(), modul);
        forReturn.coef[2] = (Zp) forReturn.coef[2].add(((Zp) coef[1].multiply(other.coef[3], modul)).negate(), modul);
        forReturn.coef[3] = (Zp) forReturn.coef[3].add(((Zp) coef[2].multiply(other.coef[1], modul)).negate(), modul);

        forReturn.coef[1] = (Zp) forReturn.coef[1].add(coef[1].multiply(other.coef[0], modul), modul);
        forReturn.coef[1] = (Zp) forReturn.coef[1].add(coef[2].multiply(other.coef[3], modul), modul);

        forReturn.coef[2] = (Zp) forReturn.coef[2].add(coef[2].multiply(other.coef[0], modul), modul);
        forReturn.coef[2] = (Zp) forReturn.coef[2].add(coef[3].multiply(other.coef[1], modul), modul);

        forReturn.coef[3] = (Zp) forReturn.coef[3].add(coef[1].multiply(other.coef[2], modul), modul);
        forReturn.coef[3] = (Zp) forReturn.coef[3].add(coef[3].multiply(other.coef[0], modul), modul);


        forReturn.normCoeff();

        return forReturn;
    }

    public Quaternion add(Quaternion other) throws IOException
    {
        Quaternion forReturn = new Quaternion();
        forReturn.modul = modul;
        for(int i = 0; i < 4; i++)
            forReturn.coef[i] = (Zp) coef[i].add(other.coef[i], modul);
        forReturn.normCoeff();
        return forReturn;
    }

    public void negate() throws IOException
    {
        for(int i = 0; i < 4; i++)
            coef[i] = coef[i].negate();

        this.normCoeff();

    }

    public String toString()
    {
        this.normCoeff();
        StringBuffer str = new StringBuffer("");
        char[] ch = {' ', 'i', 'j','k'};
        str.append(coef[0].toString());
        for(int i = 1; i < 4; i++)
        {
            if(coef[i].compareTo(Zp.zero)  > 0)
                str.append(" + ");
            if(coef[i].compareTo(Zp.zero)  != 0)
                str.append(coef[i].toString() + ch[i]);
        }
        if(str.toString().equals(""))
            return "0";
        return str.toString();
    }

    public Zp norm() throws IOException
    {
        Zp forReturn = new Zp("0");
        for(int i = 0; i < 4; i++)
            forReturn = ((Zp)forReturn.add(((Zp) coef[i].pow(2)).mod(modul), null)).mod(modul);
        int exponent = Integer.parseInt(modul.toString());
        exponent = (exponent - 1) / 2;
        int symbolLegendre = Integer.parseInt( ((Zp) forReturn.pow(exponent)).mod(modul).toString());
        if(symbolLegendre == -1)
        {
            return null;
        }
        else if(symbolLegendre == 0)
            return Zp.zero;
        forReturn = (Zp) forReturn.pow(new Zp("2").modInverse(modul).mod(((Zp)modul.add(Zp.unit.negate(), null))), modul);
        return forReturn;
    }

    public Quaternion zero() throws NumberFormatException, IOException
    {
        return new Quaternion(0, 0, 0, 0, Integer.parseInt(modul.toString()));
    }

    public Quaternion unit() throws NumberFormatException, IOException
    {
        return new Quaternion(1, 0, 0, 0, Integer.parseInt(modul.toString()));
    }

    public boolean compareTo(Quaternion other) throws IOException
    {
        for(int i = 0; i < 4; i++)
            if(other.coef[i].compareTo(coef[i]) != 0)
                return false;
        return true;
    }

    public Quaternion divide(Quaternion other) throws NumberFormatException, IOException
    {
        if(other.compareTo(this.zero()))
        {
            return new Quaternion(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
                    Integer.MAX_VALUE, Integer.parseInt(modul.toString()));
        }
        Quaternion forReturn = new Quaternion(0, 0, 0, 0, Integer.parseInt(modul.toString()));
        forReturn = this.multiply(other.inverse());
        forReturn.normCoeff();
        return forReturn;
    }

    public Quaternion divide(int n) throws NumberFormatException, IOException
    {

        if(n == 0)
        {
            return new Quaternion(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE,
                    Integer.MAX_VALUE, Integer.parseInt(modul.toString()));
        }
        Quaternion forReturn = this.zero();
        Zp nInverse = new Zp(Integer.toString(n));
        nInverse = nInverse.modInverse(modul);
        for(int i = 0; i < 4; i++)
            forReturn.coef[i] = (Zp) coef[i].multiply(nInverse, modul);
        forReturn.normCoeff();
        return forReturn;
    }

    public Quaternion inverse() throws NumberFormatException, IOException
    {
        if(this.compareTo(this.zero()))
        {
            return this.zero();
        }
        Quaternion forReturn = new Quaternion(0, 0, 0, 0, Integer.parseInt(modul.toString()));
        forReturn.coef[0] = coef[0];
        for(int i = 1; i < 4; i++)
            forReturn.coef[i] = coef[i].negate();
        Zp temp = new Zp("0");
        for(int i = 0; i < 4; i++)
            temp = ((Zp)temp.add(((Zp) forReturn.coef[i].pow(2)).mod(modul), null)).mod(modul);
        forReturn = forReturn.divide(Integer.parseInt(temp.toString()));
        forReturn.normCoeff();
        return forReturn;
    }

    public Quaternion conjugate() throws NumberFormatException, IOException
    {
        Quaternion forReturn = this.zero();
        forReturn.coef[0]= this.coef[0];
        for(int i = 1; i < 4; i++)
            forReturn.coef[i] = coef[i].negate();
        forReturn.normCoeff();
        return forReturn;
    }

    public Quaternion commutator(Quaternion other) throws NumberFormatException, IOException
    {
        Quaternion forReturn = this.zero();
        forReturn = this.multiply(other).multiply(this.inverse()).multiply(other.inverse());
        forReturn.normCoeff();
        return forReturn;
    }

    public Quaternion associator(Quaternion a, Quaternion b) throws NumberFormatException, IOException
    {
        Quaternion forReturn = this.zero();
        forReturn = (this.multiply(a)).multiply(b);
        forReturn = forReturn.add((this.multiply((a).multiply(b))).multiply(-1));
        forReturn.normCoeff();
        return forReturn;
    }

    private void normCoeff()
    {
        for(int i = 0; i < 4; i++)
            coef[i] = coef[i].mod(modul);
    }
    public Quaternion pow(int n) throws IOException {
        Quaternion forReturn = this.unit();
        for(int i = 0; i < n; i++) {
            forReturn = forReturn.multiply((Quaternion) forReturn.multiply(this));
        }
        return forReturn;
    }
}