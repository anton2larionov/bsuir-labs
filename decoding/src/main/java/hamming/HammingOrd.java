package hamming;

public class HammingOrd extends HammingCode {

    /**
     * Код Хэмминга.
     *
     * @param r степень: n = 2^r - 1
     */
    public HammingOrd(int r) {
        super(r);
    }

    @Override
    protected boolean condition(int i) {
        return i <= getN();
    }

    @Override
    protected void setNM() {
        int r = getR();
        setM(r);
        setN((1 << r) - 1);
    }

    @Override
    protected int[] intToBitArray(int i) {
        int[] row = new int[getM()];

        int p = 0;
        while (i > 0) {
            row[p] = i % 2;
            i >>= 1;
            p++;
        }
        return row;
    }
}
