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

        byte[] bytes = Files.readAllBytes(Paths.get(inFileName));
        byte[] out = new byte[bytes.length];
        int[] keys = new int[bytes.length];

        Arrays.setAll(keys, i -> iterator.next());

        for (int i = 0; i < bytes.length; i++) out[i] = xor(bytes[i], keys[i]);

        Files.write(Paths.get(outFileName), out);

        if (printKey) printKeys(outFileName + "_keys", keys);
    }

    /**
     * Данная операция эквивалентна по-битовому XOR k.
     * @param b байт
     * @param k ключ
     * @return ~b, если k равен 1, иначе b
     */
    private static byte xor(byte b, int k) {
        return (byte) (Byte.toUnsignedInt(b) ^ k);
    }

    private static void printKeys(String outFileName, int[] keys) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(outFileName))) {
            for (int i = 1; i <= keys.length; i++) {
                int k = i - 1;
                writer.write(String.valueOf(keys[k] == -1 ? 1 : keys[k]));
                if (i % 128 == 0) writer.newLine(); else writer.write(",");
            }
        }
    }
}
