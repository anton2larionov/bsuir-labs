package idea;

import java.io.IOException;
import java.nio.channels.SeekableByteChannel;

import static idea.IOUtil.BLOCK_SIZE;
import static java.nio.file.Files.newByteChannel;
import static java.nio.file.Paths.get;
import static java.nio.file.StandardOpenOption.*;

/**
 * Шифрование или дешифрования файла с помощью IDEA.
 */
public final class FileEncryption {

    /**
     * Шифрования файла.
     */
    public static void cryptFile(String inputFileName, String outputFileName, Idea idea) throws IOException {

        try (SeekableByteChannel inChannel = newByteChannel(get(inputFileName), READ);
             SeekableByteChannel outChannel = newByteChannel(get(outputFileName), CREATE, TRUNCATE_EXISTING, WRITE)) {

            long inDataLen = inChannel.size();
            long outDataLen = (inDataLen + BLOCK_SIZE - 1) / BLOCK_SIZE * BLOCK_SIZE;

            IOUtil.pumpData(inChannel, inDataLen, outChannel, outDataLen, idea);
            IOUtil.writeDataLength(outChannel, inDataLen, idea);
        }
    }

    /**
     * Дешифрования файла.
     */
    public static void decryptFile(String inputFileName, String outputFileName, Idea idea) throws IOException {

        try (SeekableByteChannel inChannel = newByteChannel(get(inputFileName), READ);
             SeekableByteChannel outChannel = newByteChannel(get(outputFileName), CREATE, TRUNCATE_EXISTING, WRITE)) {

            long inFileSize = inChannel.size();

            if (inFileSize % BLOCK_SIZE != 0) {
                throw new IOException("Размер входного файла не кратен " + BLOCK_SIZE + " байтам.");
            }

            long inDataLen = inFileSize - BLOCK_SIZE;

            IOUtil.pumpData(inChannel, inDataLen, outChannel, inDataLen, idea);

            long outFileSize = IOUtil.readDataLength(inChannel, idea);
            if (outFileSize < 0 || outFileSize > inDataLen || outFileSize < inDataLen - BLOCK_SIZE + 1) {
                throw new IOException("Входной файл не является зашифрованным.");
            }
            if (outFileSize != inDataLen) {
                outChannel.truncate(outFileSize);
            }
        }
    }
}
