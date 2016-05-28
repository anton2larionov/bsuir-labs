package idea;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.util.Arrays;

/**
 * Утилиты для чтения/записи.
 */
public final class IOUtil {

    public final static int BLOCK_SIZE = 8;

    public static void pumpData(ByteChannel inChannel, long inDataLen,
                                ByteChannel outChannel, long outDataLen, Idea idea) throws IOException {
        final int bufSize = 4096;
        ByteBuffer buf = ByteBuffer.allocate(bufSize);
        long filePos = 0;

        while (filePos < inDataLen) {
            int reqLen = (int) java.lang.Math.min(inDataLen - filePos, bufSize);
            buf.position(0);
            buf.limit(reqLen);
            int trLen = inChannel.read(buf);
            if (trLen != reqLen) {
                throw new IOException("Неполное чтение блока данных из файла.");
            }
            int chunkLen = (trLen + BLOCK_SIZE - 1) / BLOCK_SIZE * BLOCK_SIZE;
            Arrays.fill(buf.array(), trLen, chunkLen, (byte) 0);
            for (int pos = 0; pos < chunkLen; pos += BLOCK_SIZE) {
                idea.crypt(buf.array(), pos);
            }
            reqLen = (int) java.lang.Math.min(outDataLen - filePos, chunkLen);
            buf.position(0);
            buf.limit(reqLen);
            trLen = outChannel.write(buf);
            if (trLen != reqLen) {
                throw new IOException("Неполная запись блока данных в файл.");
            }
            filePos += chunkLen;
        }
    }

    public static long readDataLength(ByteChannel channel, Idea idea) throws IOException {
        ByteBuffer buf = ByteBuffer.allocate(BLOCK_SIZE);
        int trLen = channel.read(buf);
        if (trLen != BLOCK_SIZE) {
            throw new IOException("Ошибка чтения блока данных, требуемой длины.");
        }
        byte[] a = buf.array();
        idea.crypt(a, 0);
        return unpackDataLength(a);
    }

    public static void writeDataLength(ByteChannel channel, long dataLength, Idea idea) throws IOException {
        byte[] a = packDataLength(dataLength);
        idea.crypt(a, 0);
        ByteBuffer buf = ByteBuffer.wrap(a);
        int trLen = channel.write(buf);
        if (trLen != BLOCK_SIZE) {
            throw new IOException("Ошибка записи блока данных, требуемой длины.");
        }
    }

    // Упаковка целого в 8-байтный блок.
    private static byte[] packDataLength(long i) {
        byte[] b = new byte[BLOCK_SIZE];
        b[7] = (byte) (i << 3);
        b[6] = (byte) (i >> 5);
        b[5] = (byte) (i >> 13);
        b[4] = (byte) (i >> 21);
        b[3] = (byte) (i >> 29);
        b[2] = (byte) (i >> 37);
        return b;
    }

    // Распаковка целого из 8-байтного блока.
    private static long unpackDataLength(byte[] b) {
        if (b[0] != 0 || b[1] != 0 || (b[7] & 7) != 0) {
            return -1;
        }
        return (long) (b[7] & 0xFF) >> 3 |
                (long) (b[6] & 0xFF) << 5 |
                (long) (b[5] & 0xFF) << 13 |
                (long) (b[4] & 0xFF) << 21 |
                (long) (b[3] & 0xFF) << 29 |
                (long) (b[2] & 0xFF) << 37;
    }
}
