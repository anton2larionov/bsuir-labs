package idea;

import static idea.Math.add;
import static idea.Math.mul;

/**
 * Алгоритм IDEA (International Data Encryption Algorithm).
 */
public class Idea {

    // число раундов
    public static final int ROUNDS = 8;

    // подключи шифрования
    private final int[] subKey;

    public Idea(int[] subKey) {
        if (subKey.length != 52) {
            throw new IllegalArgumentException("Число ключей должно быть равно 52.");
        }
        this.subKey = subKey;
    }

    /**
     * Шифровать или дешифровать блок данных 8 байт.
     *
     * @param data блок данных
     * @param pos  стартовая позиция
     */
    public void crypt(byte[] data, int pos) {
        int x0 = ((data[pos] & 0xFF) << 8) | (data[pos + 1] & 0xFF);
        int x1 = ((data[pos + 2] & 0xFF) << 8) | (data[pos + 3] & 0xFF);
        int x2 = ((data[pos + 4] & 0xFF) << 8) | (data[pos + 5] & 0xFF);
        int x3 = ((data[pos + 6] & 0xFF) << 8) | (data[pos + 7] & 0xFF);
        // 8 раундов
        int p = 0;
        for (int round = 0; round < ROUNDS; round++) {
            int y0 = mul(x0, subKey[p++]);
            int y1 = add(x1, subKey[p++]);
            int y2 = add(x2, subKey[p++]);
            int y3 = mul(x3, subKey[p++]);
            //
            int t0 = mul(y0 ^ y2, subKey[p++]);
            int t1 = add(y1 ^ y3, t0);
            int t2 = mul(t1, subKey[p++]);
            int t3 = add(t0, t2);
            //
            x0 = y0 ^ t2;
            x1 = y2 ^ t2;
            x2 = y1 ^ t3;
            x3 = y3 ^ t3;
        }
        // финальная трансформация
        int r0 = mul(x0, subKey[p++]);
        int r1 = add(x2, subKey[p++]);
        int r2 = add(x1, subKey[p++]);
        int r3 = mul(x3, subKey[p]);
        // выходная информация
        data[pos] = (byte) (r0 >> 8);
        data[pos + 1] = (byte) r0;
        data[pos + 2] = (byte) (r1 >> 8);
        data[pos + 3] = (byte) r1;
        data[pos + 4] = (byte) (r2 >> 8);
        data[pos + 5] = (byte) r2;
        data[pos + 6] = (byte) (r3 >> 8);
        data[pos + 7] = (byte) r3;
    }
}
