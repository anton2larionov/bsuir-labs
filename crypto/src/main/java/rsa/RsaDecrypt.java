package rsa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static java.lang.Byte.toUnsignedInt;

public class RsaDecrypt {

    // модуль
    private final int r;
    // секретный ключ
    private final int d;

    public RsaDecrypt(int r, int d) {
        if (r < 2 || d < 2) {
            throw new IllegalArgumentException("Числа долны быть больше 2.");
        }
        this.r = r;
        this.d = d;
    }

    public void decryptFile(String inFileName, String outFileName) throws IOException {
        byte[] encryptedBytes = Files.readAllBytes(Paths.get(inFileName));
        byte[] bytes = new byte[encryptedBytes.length / 2];

        for (int i = 0; i < bytes.length; i++) {
            byte[] tmp = {encryptedBytes[i * 2], encryptedBytes[i * 2 + 1]};

            bytes[i] = fastExpDecrypt(tmp);
        }
        Files.write(Paths.get(outFileName), bytes);
    }

    private byte fastExpDecrypt(byte[] x) {
        int z = (toUnsignedInt(x[0]) << 8) | toUnsignedInt(x[1]);
        return (byte) MathUtil.fastExp(z, d, r);
    }
}
