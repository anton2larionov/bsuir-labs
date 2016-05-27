package basic.vfs.impl;

import basic.vfs.interfaces.IDirectory;
import basic.vfs.interfaces.IMountPoint;
import basic.vfs.interfaces.IVFS;
import basic.vfs.interfaces.IVFSObject;

import java.util.Date;
import java.util.Set;

/**
 * Точка монтирования виртуальной файловой системы.
 */
public class MountPoint implements IMountPoint {

    private final Date creationDate;
    private IDirectory dir;
    private Set<IVFSObject> prevSet;

    MountPoint(IDirectory dir, IVFS ivfs) {
        this.dir = dir;
        setVFS(ivfs);
        creationDate = new Date();
    }

    @Override
    public void setVFS(IVFS ivfs) {
        prevSet = dir.getVFSObjects();
        dir.setVFSObjects(ivfs.root().getVFSObjects());
    }

    @Override
    public void unmount() {
        dir.setVFSObjects(prevSet);
    }

    @Override
    public IDirectory root() {
        return dir;
    }

    @Override
    public long size() {
        return root().size();
    }

    @Override
    public Date creationDate() {
        return creationDate;
    }

    @Override
    public String getName() {
        return dir.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MountPoint)) return false;

        MountPoint that = (MountPoint) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().toLowerCase().hashCode();
    }

}
