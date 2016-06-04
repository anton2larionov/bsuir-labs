package stream.rc4;

import stream.KeyIterator;

import java.util.stream.IntStream;

/**
 * Алгоритм RC4
 */
public class RC4 implements KeyIterator {

    private static int T = 256;

    // блок замены
    private final int[] s = IntStream.range(0, T).toArray();
    // счетчики
    private int a, b;

    /**
     * @param u пользовательский ключ
     */
    public RC4(StringRC4Key u) {
        modify(u.userKey());
    }

    @Override
    public int next() {
        a = (a + 1) % T;
        b = (b + s[a]) % T;
        swap(s, a, b);
        return s[(s[a] + s[b]) % T];
    }

    // изменение блока замены
    private void modify(int[] u) {
        int j = 0;
        for (int i = 0; i < T; i++) {
            j = (j + s[i] + u[i % u.length]) % T;
            swap(s, i, j);
        }
    }

    // перестановка элементов массива
    private static void swap(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
}
