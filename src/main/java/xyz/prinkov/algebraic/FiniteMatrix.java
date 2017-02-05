package xyz.prinkov.algebraic;

import xyz.prinkov.algebraic.finite.FiniteSemiRing;

import java.util.ArrayList;



public class FiniteMatrix <T extends FiniteSemiRing>
{
    private int rows, cols, currentIndex;
    private ArrayList<T> array ;

    public FiniteMatrix()
    {
        rows = 0;
        cols = 0;
        currentIndex = 0;
    }

    public FiniteMatrix(int rows_, int cols_, T zero)
    {
        rows = rows_;
        cols = cols_;
        currentIndex = 0;
        array = new ArrayList<T>(rows*cols);
        array.add(zero);
        zero();
    }

    @SuppressWarnings("unchecked")
    public FiniteMatrix(FiniteMatrix<?> other)
    {
        rows = other.rows;
        cols = other.cols;
        array = new ArrayList<T>(rows*cols);
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
                array.add(i * cols + j, (T) other.array.get(i * cols + j));
        currentIndex = rows * cols;
    }

    public FiniteMatrix<T> add(T element)
    {
        this.array.add(currentIndex % rows * cols ,element);
        return this;
    }

    public T get(int i, int j)
    {
        return array.get(i * cols + j);
    }

    public void set(int i, int j, T element)
    {
        this.array.add(i * cols + j,element);
    }

    public String toString()
    {
        StringBuffer str = new StringBuffer();
        for(int i = 0; i < rows; i++)
        {
            for(int j = 0; j < cols; j++)
                str.append(this.get(i, j)+" ");
            str.append("\n");
        }
        return str.toString();
    }

    @SuppressWarnings("unchecked")
    public void zero()
    {
        currentIndex = 0;
        while(currentIndex < rows * cols)
            array.add(currentIndex++, (T) array.get(0).getZero());
    }

    @SuppressWarnings("unchecked")
    public void eye()
    {
        zero();
        int diag = 0;
        while(diag < rows * cols)
        {
            array.set(diag, (T) array.get(0).unit());
            diag += cols+1;
        }
    }

    @SuppressWarnings("unchecked")
    public void unit()
    {
        currentIndex = 0;
        while(currentIndex < rows * cols)
            array.add(currentIndex++, (T) array.get(0).unit());
    }

    public FiniteMatrix<T> read(T element)
    {
        array.set(currentIndex % (rows * cols), element);
        currentIndex++;
        return this;
    }

    @SuppressWarnings("unchecked")
    public FiniteMatrix<T> add(FiniteMatrix<T> other)
    {
        int index = 0;
        FiniteMatrix<T> forReturn = new FiniteMatrix<T>(rows, cols, (T)array.get(0));
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
            {
                index = i * cols + j;
                forReturn.array.set(index ,(T)array.get(index).add(other.array.get(index), null));
            }

        return forReturn;
    }

    @SuppressWarnings("unchecked")
    public FiniteMatrix<T> multiply(FiniteMatrix<T> other)
    {
        FiniteMatrix<T> forReturn = new FiniteMatrix<T>(rows, other.cols, (T)array.get(0));
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < other.cols; col++) {
                for (int inner = 0; inner < cols; inner++) {
                    forReturn.array.set(row * cols + col ,(T) forReturn.array.get(row * cols + col)
                            .add((T) ((T) array.get(row * cols + inner))
                                    .multiply(other.array.get(inner * cols + col), null), null));
                }
            }
        }
        return forReturn;
    }

    @SuppressWarnings("unchecked")
    public FiniteMatrix<T> multiply(T t)
    {
        int index = 0;
        FiniteMatrix<T> forReturn = new FiniteMatrix<T>(this);
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++)
            {
                index = i * cols + j;
                forReturn.array.set(index, (T) array.get(index).multiply(t, null));
            }
        return forReturn;
    }

    public FiniteMatrix<T> pow(int n)
    {
        FiniteMatrix<T> forReturn = new FiniteMatrix<T>(this);
        forReturn.eye();
        for(int i = 0; i < n; i++)
            forReturn = forReturn.multiply(this);
        return forReturn;
    }

	/*public boolean compareTo(FiniteMatrix<T> other)
	{
		for(int i = 0; i < rows; i++)
			for(int j = 0; j < cols; j++)
				if(this.get(i, j).compareTo(other.get(i, j)) != 0)
						return false;
		return true;
	}*/

    public FiniteMatrix<T> clone()
    {
        return new FiniteMatrix<T>(this);
    }

    public FiniteMatrix<T> star(int n)
    {
        FiniteMatrix<T> forReturn = new FiniteMatrix<T>(this);
        forReturn.eye();
        for(int i = 1; i < n; i++)
            forReturn = forReturn.add(this.pow(i));
        return forReturn;
    }



    @SuppressWarnings("unchecked")
    public T permanent()
    {
        T per = (T) array.get(0).getZero();
        int n = rows;
        int temp = 0;
        if(n == 1)
            return  this.get(0, 0);
        for(int i = 0; i < n; i++)
        {
            FiniteMatrix<T> minor = new FiniteMatrix<T>(n-1, n-1, (T)array.get(0).getZero());
            for(int j = 0; j < n - 1; j++)
                for(int k =0; k < n - 1; k++)
                {
                    if(j >= i) temp = 1;
                    else temp = 0;
                    minor.array.set(j * cols + k, this.get(j + temp, k+1));
                }
            per = (T) per.add(this.get(i, 0).multiply(minor.permanent(), null), null);
        }
        return per;
    }

    public int getRows()
    {
        return rows;
    }

    public int getCols()
    {
        return cols;
    }

}