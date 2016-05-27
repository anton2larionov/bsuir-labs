package basic.vfs.interfaces;

/**
 * Интерфейс содержит методы доступа к
 * конкретному файлу виртуальной файловой системы.
 */
public interface IFile extends IVFSObject {

    // является ли файл исполняющим
    boolean isExec();

}
