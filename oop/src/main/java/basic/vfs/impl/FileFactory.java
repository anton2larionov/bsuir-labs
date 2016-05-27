package basic.vfs.impl;

import basic.vfs.interfaces.IFile;

/**
 * Фабрика файлов виртуальной файловой системы.
 */
public class FileFactory {

    // получить обычный файл
    public IFile getFile(String fileName) {
        return new File(fileName) {
            @Override
            public boolean isExec() {
                return false;
            }

            @Override
            public long size() {
                return 1024;
            }
        };
    }

    // получить исполняющий файл
    public IFile getExecFile(String fileName) {
        return new File(fileName) {
            @Override
            public boolean isExec() {
                return true;
            }

            @Override
            public long size() {
                return 4096;
            }
        };
    }

}
