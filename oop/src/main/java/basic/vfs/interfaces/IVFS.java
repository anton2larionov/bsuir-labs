package basic.vfs.interfaces;

import java.nio.charset.Charset;

/**
 * Интерфейс содержит методы доступа к метаинформации
 * конкретного драйвера виртуальной файловой системы.
 */
public interface IVFS extends Nameable {

    // корень виртуальной файловой системы
    IDirectory root();

    // кодировка имен файлов
    Charset charset();

}
