package hamming;

public class HammingExt extends HammingCode {

    /**
     * Расширенный Код Хэмминга.
     *
     * @param r степень: n = 2^r
     */
    public HammingExt(int r) {
        super(r);
    }

    @Override
    protected boolean condition(int i) {
        return i < getN();
    }

    @Override
    protected void setNM() {
        int r = getR();
        setM(r + 1);
        setN(1 << r);
    }

    @Override
    protected int[] intToBitArray(int i) {
        int m = getM();
        int r = getR();

        int[] row = new int[m];
        row[r] = 1;    // дополнительная строка

        int p = r - 1;
        while (i > 0) {
            row[p] = i % 2;
            row[r] ^= row[p];    // приведение к систематическому виду
            i >>= 1;
            p--;
        }
        return row;
    }
}