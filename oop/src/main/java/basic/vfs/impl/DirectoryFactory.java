package basic.vfs.impl;

import basic.vfs.interfaces.IDirectory;

/**
 * Фабрика каталогов виртуальной файловой системы.
 */
public class DirectoryFactory {

    public IDirectory getDir(String name) {
        return new Directory(name);
    }

}
