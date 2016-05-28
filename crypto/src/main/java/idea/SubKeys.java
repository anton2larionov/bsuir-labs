package idea;

import static idea.Idea.ROUNDS;
import static idea.Math.addInv;
import static idea.Math.mulInv;

public final class SubKeys {

    private final int[] keys;

    public SubKeys(StringKey key) {
        this.keys = expands(key.binaryKey());
    }

    /**
     * @param encrypt {@code true} для шифрования, {@code false} для дешифрования
     * @return 52 подключа
     */
    public int[] getKeys(boolean encrypt) {
        return encrypt ? keys : invert(keys);
    }

    private static int[] expands(byte[] binaryKey) {
        int[] key = new int[ROUNDS * 6 + 4];
        for (int i = 0; i < binaryKey.length / 2; i++) {
            key[i] = ((binaryKey[2 * i] & 0xFF) << 8) | (binaryKey[2 * i + 1] & 0xFF);
        }
        for (int i = binaryKey.length / 2; i < key.length; i++) {
            key[i] = ((key[(i + 1) % 8 != 0 ? i - 7 : i - 15] << 9) | (key[(i + 2) % 8 < 2 ? i - 14 : i - 6] >> 7)) & 0xFFFF;
        }
        return key;
    }

    // Инвертировать подключи.
    private static int[] invert(int[] key) {
        int[] invKey = new int[key.length];
        int p = 0;
        int i = ROUNDS * 6;
        invKey[i] = mulInv(key[p++]);
        invKey[i + 1] = addInv(key[p++]);
        invKey[i + 2] = addInv(key[p++]);
        invKey[i + 3] = mulInv(key[p++]);
        for (int r = ROUNDS - 1; r >= 0; r--) {
            i = r * 6;
            int m = r > 0 ? 2 : 1;
            int n = r > 0 ? 1 : 2;
            invKey[i + 4] = key[p++];
            invKey[i + 5] = key[p++];
            invKey[i] = mulInv(key[p++]);
            invKey[i + m] = addInv(key[p++]);
            invKey[i + n] = addInv(key[p++]);
            invKey[i + 3] = mulInv(key[p++]);
        }
        return invKey;
    }
}
