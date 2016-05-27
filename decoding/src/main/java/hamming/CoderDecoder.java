package hamming;

import java.util.HashMap;
import java.util.Map;

public class CoderDecoder {

    private final int[][] mG, mH;
    private final int k, n, m;

    /**
     * Карта исходных сообщений и кодовых векторов.
     */
    private Map<String, String> codeDecode = new HashMap<>();

    /**
     * Карта синдромов и векторов ошибок.
     */
    private Map<String, int[]> sindromErr = new HashMap<>();

    /**
     * Кодер-декодер.
     *
     * @param mG порождающая матрица [k x n]
     * @param mH проверочная матрица [(n-k) x n]
     */
    public CoderDecoder(int[][] mG, int[][] mH) {
        k = mG.length;
        n = mG[0].length;
        m = n - k;

        if (k > n) {
            throw new IllegalArgumentException("k > n");
        }
        if (m != mH.length) {
            throw new IllegalArgumentException("m != mH.length");
        }
        if (n != mH[0].length) {
            throw new IllegalArgumentException("n != mH[0].length");
        }
        if (multiply(mG, mH) != 0) {
            throw new IllegalArgumentException("G * HT != 0");
        }

        this.mG = mG;
        this.mH = mH;

        fillCDmap();
        fillSindromMap();
    }

    /**
     * Заполнить карту исходных сообщений и кодовых векторов.
     */
    private void fillCDmap() {
        int qK = 1 << k;

        for (int i = 0; i < qK; i++) {
            String u = addNull(Integer.toBinaryString(i), k);
            String v = code(u);
            codeDecode.put(v, u);
        }
    }

    /**
     * Заполнить карту синдромов и векторов ошибок.
     */
    private void fillSindromMap() {
        int qN = 1 << n;

        for (int i = 0; i < qN; i++) {
            int[] w = intToBinarr(i, n);
            // синдром
            int[] s = getSindrom(w);

            // если получен ненулевой синдром
            if (weight(s) != 0) {
                String key = arrayToString(s);
                // вектор ошибок
                int[] e = sindromErr.get(key);
                if ((e == null) || (weight(w) < weight(e))) {
                    // сохранение синдрома с меньшим весом
                    sindromErr.put(key, w);
                }
            }
        }
    }

    /**
     * Проверка условия (G * Ht) == 0.
     */
    private int multiply(int[][] mG, int[][] mH) {
        int[][] s = new int[k][m];
        int r = 0;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < m; j++) {
                for (int q = 0; q < n; q++) {
                    s[i][j] ^= mG[i][q] * mH[j][q];
                }
                r += s[i][j];
            }
        }
        return r;
    }

    /**
     * @param word исходное сообщение
     * @return кодовое слово
     */
    public String code(String word) {
        String s = addNull(word, k);

        int[] u = new int[k];
        for (int i = 0; i < k; i++) {
            u[i] = Integer.parseInt(s.substring(i, i + 1), 2);
        }

        return arrayToString(code(u));
    }

    /**
     * @param code кодовое слово
     * @return декодированное сообщение
     */
    public String decode(String code) {
        String str = addNull(code, n);

        int[] w = new int[n];
        for (int i = 0; i < n; i++) {
            w[i] = Integer.parseInt(str.substring(i, i + 1), 2);
        }
        // синдром
        int[] s = getSindrom(w);

		/*
         * если получен синдром (нет ошибок), то выполняется поиск
		 * исходного слова в карте исходных сообщений и
		 * кодовых векторов
		 */
        if (weight(s) == 0) {
            return codeDecode.get(str);
        }

        // получение вектора ошибок по синдрому
        int[] e = sindromErr.get(arrayToString(s));

        // коррекция принятого кодового слова
        for (int i = 0; i < n; i++) {
            w[i] ^= e[i];
        }

        return codeDecode.get(arrayToString(w));
    }

    /**
     * Кодирование сообщения с использованием порождающей матрицы G.
     *
     * @param u сообщениe
     * @return кодовое слово
     */
    private int[] code(int[] u) {
        if (u.length != k) {
            throw new IllegalArgumentException();
        }

        int[] v = new int[n];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < k; j++) {
                v[i] ^= mG[j][i] * u[j];
            }
        }

        return v;
    }

    /**
     * @param w кодовый вектор
     * @return синдром
     */
    private int[] getSindrom(int[] w) {
        int s[] = new int[m];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                s[i] ^= w[j] * mH[i][j];
            }
        }
        return s;
    }

    /**
     * @param s синдром
     * @return вес
     */
    private int weight(int[] s) {
        int r = 0;
        for (int value : s) {
            r += value;
        }
        return r;
    }

    private String arrayToString(int[] a) {
        StringBuilder b = new StringBuilder();
        for (int anA : a) {
            b.append(String.valueOf(anA));
        }
        return b.toString();
    }

    private int[] intToBinarr(int x, int length) {
        String s = addNull(Integer.toBinaryString(x), length);

        int[] u = new int[length];
        for (int i = 0; i < length; i++) {
            u[i] = Integer.parseInt(s.substring(i, i + 1), 2);
        }
        return u;
    }

    /**
     * Дополнить строку нулями с начала.
     *
     * @param s строка
     * @param c требуемое количество символов
     * @return строка, дополненная нулями
     */
    private String addNull(String s, int c) {
        if (s.length() > c) {
            throw new IllegalArgumentException(s.length() + " > " + c);
        }
        while (s.length() < c) {
            s = "0" + s;
        }
        return s;
    }
}
