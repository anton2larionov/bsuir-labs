package stream;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Шифрование или дешифрования файла с помощью потокового алгоритма.
 */
public class FilesUtil {

    /**
     * Шифрование/дешифрование файла.
     */
    public static void crypt(String inFileName, String outFileName,
                             KeyIterator iterator, boolean printKey) throws IOException {

        // чтение файла в массив байт
        byte[] bytes = Files.readAllBytes(Paths.get(inFileName));

        // преобразование байтов в массивы бит
        int[][] bits = new int[bytes.length][];
        for (int i = 0; i < bytes.length; i++) {
            bits[i] = toBinaryArray(Byte.toUnsignedInt(bytes[i]), 8);
        }

        // генерация ключевой последовательности
        int[] keys = new int[bytes.length * 8];
        Arrays.setAll(keys, i -> iterator.next());

        // шифрование каждого бита
        int[][] outBits = new int[bytes.length][8];
        for (int i = 0; i < bits.length; i++) {
            for (int j = 0; j < 8; j++) {
                outBits[i][j] = bits[i][j] ^ keys[i * 8 + j];
            }
        }

        // преобразование массивов бит в байты
        byte[] outBytes = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            outBytes[i] = (byte) fromBinaryArray(outBits[i]);
        }

        // запись зашифрованных байтов
        Files.write(Paths.get(outFileName), outBytes);

        if (printKey) printKeys(outFileName + "_keys", keys);
    }

    private static void printKeys(String outFileName, int[] keys) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFileName))) {
            for (int i = 1; i <= keys.length; i++) {
                writer.write(String.valueOf(keys[i - 1]));
                if (i % 128 == 0) writer.newLine(); else if (i != keys.length) writer.write(",");
            }
        }
    }

    private static int[] toBinaryArray(int val, int chars) {
        int[] buf = new int[chars];
        Arrays.fill(buf, digits[0]);

        do {
            buf[--chars] = digits[val & 1];
            val >>>= 1;
        } while (val != 0 && chars > 0);

        return buf;
    }

    private static int fromBinaryArray(int[] arr) {
        int val = 0;

        for (int i = 0; i < arr.length; i++) {
            val += (arr[arr.length - 1 - i] << i);
        }

        return val;
    }

    private final static int[] digits = {0 , 1};
}
