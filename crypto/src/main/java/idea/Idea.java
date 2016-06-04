package idea;

import static idea.MathUtil.add;
import static idea.MathUtil.mul;
import static java.lang.Byte.toUnsignedInt;

/**
 * Алгоритм IDEA (International Data Encryption Algorithm).
 */
public class IDEA {

    // число раундов
    public static final int ROUNDS = 8;

    // подключи шифрования
    private final int[] subKey;

    public IDEA(int[] subKey) {
        if (subKey.length != 52) {
            throw new IllegalArgumentException("Число ключей должно быть равно 52.");
        }
        this.subKey = subKey;
    }

    /**
     * Шифровать или дешифровать блок данных 8 байт.
     *
     * @param data блок данных
     */
    public void crypt(byte[] data) {
        if (data.length != 8) {
            throw new IllegalArgumentException("Число байт должно быть равно 8.");
        }

        int x0 = (toUnsignedInt(data[0]) << 8) | toUnsignedInt(data[1]);
        int x1 = (toUnsignedInt(data[2]) << 8) | toUnsignedInt(data[3]);
        int x2 = (toUnsignedInt(data[4]) << 8) | toUnsignedInt(data[5]);
        int x3 = (toUnsignedInt(data[6]) << 8) | toUnsignedInt(data[7]);
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
        data[0] = (byte) (r0 >> 8);
        data[1] = (byte) r0;
        data[2] = (byte) (r1 >> 8);
        data[3] = (byte) r1;
        data[4] = (byte) (r2 >> 8);
        data[5] = (byte) r2;
        data[6] = (byte) (r3 >> 8);
        data[7] = (byte) r3;
    }
}
