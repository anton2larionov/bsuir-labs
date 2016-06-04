package idea;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.util.Arrays;

import static java.nio.file.Files.newByteChannel;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.*;

/**
 * Шифрование или дешифрования файла с помощью IDEA.
 */
public final class FilesUtil {

    private static int BLOCK_SIZE = 8;

    /**
     * Шифрование/дешифрование файла.
     */
    public static void cryptByIdea(String inputFileName, String outputFileName, IDEA idea) throws IOException {

        try (ByteChannel outChannel = newByteChannel(get(outputFileName), CREATE, TRUNCATE_EXISTING, WRITE)) {

            byte[] in = Files.readAllBytes(get(inputFileName));

            int pos = 0;
            while (pos < in.length) {
                byte[] chunk = Arrays.copyOfRange(in, pos, pos + BLOCK_SIZE);
                idea.crypt(chunk);
                write(outChannel, chunk);
                pos += BLOCK_SIZE;
            }
        }
    }

    private static void write(ByteChannel channel, byte[] data) throws IOException {
        int len = channel.write(ByteBuffer.wrap(data));
        if (len != BLOCK_SIZE)
            throw new IOException("Ошибка записи блока данных, требуемой длины.");
    }
}
