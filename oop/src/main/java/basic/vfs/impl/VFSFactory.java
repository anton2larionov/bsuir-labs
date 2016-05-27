package basic.vfs.impl;

import basic.vfs.interfaces.IVFS;

import java.util.Objects;

/**
 * Фабрика драйверов виртуальной файловой системы.
 */
public class VFSFactory {

    public IVFS getVFS(VFSType type) {

        Objects.nonNull(type);

        switch (type) {
            case NATIVE:
                return new NativeVFS();
            case FTP:
                return new FtpVFS();
        }

        return null;
    }

    /**
     * Типы драйверов виртуальной файловой системы.
     */
    public enum VFSType {
        NATIVE, FTP
    }

}
