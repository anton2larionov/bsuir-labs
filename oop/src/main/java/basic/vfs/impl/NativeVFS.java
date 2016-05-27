package basic.vfs.impl;

import basic.vfs.interfaces.IDirectory;
import basic.vfs.interfaces.IVFS;

import java.nio.charset.Charset;

/**
 * Драйвер Native - виртуальной файловой системы.
 */
public class NativeVFS implements IVFS {

    private final IDirectory root = new Directory("root");

    NativeVFS() {
    }

    @Override
    public IDirectory root() {
        return root;
    }

    @Override
    public Charset charset() {
        return Charset.defaultCharset();
    }

    @Override
    public String getName() {
        return "NativeVFS";
    }

}
