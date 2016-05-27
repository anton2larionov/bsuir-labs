package hamming;

import java.util.Arrays;

public abstract class HammingCode {
    private final int r, k;
    private final double rateOfCode;
    private int n, m;
    private final int[][] mG, mH, mP;

    /**
     * @param r степень
     */
    public HammingCode(int r) {
        if (r < 2 || r > 20) {
            throw new IllegalArgumentException("r < 2 || r > 20");
        }
        this.r = r;
        setNM();
        k = n - m;
        rateOfCode = ((double) k) / n;
        mP = new int[k][];
        mH = new int[m][n];
        mG = new int[k][n];
        makeMatrixP();
        fillMatrixGH();
    }

    /**
     * Задать n и m.
     */
    protected abstract void setNM();

    protected void setM(int m) {
        this.m = m;
    }

    protected void setN(int n) {
        this.n = n;
    }

    /**
     * Построить матрицу P.
     */
    private void makeMatrixP() {
        int row = 0;
        for (int i = 3; condition(i); i++) {
            if (!isPow2(i)) {
                mP[row] = intToBitArray(i);
                row++;
            }
        }
    }

    protected abstract boolean condition(int i);

    /**
     * @param i число
     * @return массив, элементы которого цифры в
     * бинарном представлении числа i
     */
    protected abstract int[] intToBitArray(int i);

    /**
     * Заполнить матрицы G и H.
     */
    private void fillMatrixGH() {
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < k; j++) {
                mH[i][j] = mP[j][i];
            }
            mH[i][i + k] = 1;    // единичная матрица
        }

        for (int i = 0; i < k; i++) {
            System.arraycopy(mP[i], 0, mG[i], k, n - k);
            mG[i][i] = 1;    // единичная матрица
        }
    }

    /**
     * @param x число
     * @return true - если x = 2^y
     */
    private boolean isPow2(int x) {
        return ((x & x - 1) == 0);
    }

    public int getR() {
        return r;
    }

    public int getN() {
        return n;
    }

    public int getM() {
        return m;
    }

    public int getK() {
        return k;
    }

    /**
     * Печать матрицы H.
     */
    public void printMatrixH() {
        for (int i = 0; i < m; i++) {
            System.out.println(Arrays.toString(mH[i]));
        }
    }

    /**
     * Печать матрицы G.
     */
    public void printMatrixG() {
        for (int i = 0; i < k; i++) {
            System.out.println(Arrays.toString(mG[i]));
        }
    }

    /**
     * @return скорость кода
     */
    public double rateOfCode() {
        return rateOfCode;
    }

    /**
     * @return порождающая матрица
     */
    public int[][] getMatrixG() {
        return mG.clone();
    }

    /**
     * @return проверочная матрица
     */
    public int[][] getMatrixH() {
        return mH.clone();
    }
}