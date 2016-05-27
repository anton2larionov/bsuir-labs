package basic.vfs.interfaces;

/**
 * Точка монтирования виртуальной файловой системы.
 */
public interface IMountPoint extends IVFSObject {

    // монтировать виртуальную файловую систему
    void setVFS(IVFS ivfs);

    // корень виртуальной файловой системы
    IDirectory root();

    // размонтировать
    void unmount();
}
