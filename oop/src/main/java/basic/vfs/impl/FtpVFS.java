package basic.vfs.impl;

import basic.vfs.interfaces.IDirectory;
import basic.vfs.interfaces.IVFS;

import java.nio.charset.Charset;

/**
 * Драйвер FTP - виртуальной файловой системы.
 */
public class FtpVFS implements IVFS {

    private final IDirectory ftp_root = new Directory("ftp_root");

    FtpVFS() {
    }

    @Override
    public IDirectory root() {
        return ftp_root;
    }

    @Override
    public Charset charset() {
        return Charset.forName("UTF8");
    }

    @Override
    public String getName() {
        return "FtpVFS";
    }

}
