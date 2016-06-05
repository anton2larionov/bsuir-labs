package rsa;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RsaEncrypt {

    // модуль
    private final int r;
    // открытый ключ
    private final int e;

    public RsaEncrypt(int r, int e) {
        if (r < 2 || e < 2) {
            throw new IllegalArgumentException("Числа долны быть больше 2.");
        }
        this.r = r;
        this.e = e;
    }

    public void encryptFile(String inFileName, String outFileName) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(inFileName));
        byte[] encryptedBytes = new byte[bytes.length * 2];

        for (int i = 0; i < bytes.length; i++) {
            byte[] tmp = fastExpEncrypt(bytes[i]);

            encryptedBytes[i * 2]     = tmp[0];
            encryptedBytes[i * 2 + 1] = tmp[1];
        }
        Files.write(Paths.get(outFileName), encryptedBytes);
    }

    private byte[] fastExpEncrypt(byte x) {
        int s = MathUtil.fastExp(Byte.toUnsignedInt(x), e, r);
        byte[] bytes = new byte[2];
        bytes[0] = (byte) (s >> 8);
        bytes[1] = (byte) s;
        return bytes;
    }
}
