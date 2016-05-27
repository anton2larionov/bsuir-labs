package basic.vfs.interfaces;

import java.util.Date;

/**
 * Экземпляр записи виртуальной файловой системы.
 */
public interface IVFSObject extends Nameable {

    // размер
    long size();

    // дата создания
    Date creationDate();

}
