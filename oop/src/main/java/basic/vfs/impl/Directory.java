package basic.vfs.impl;

import basic.vfs.interfaces.IDirectory;
import basic.vfs.interfaces.IVFSObject;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Каталог виртуальной файловой системы.
 */
public class Directory implements IDirectory {

    private final Date creationDate;
    private Set<IVFSObject> ivfsObjects;
    private String name;

    Directory(String name) {
        if (name != null && name.trim().matches("[\\w .]+")) {
            this.name = name;
        } else {
            this.name = "new_dir";
        }

        ivfsObjects = new HashSet<>();
        creationDate = new Date();
    }

    @Override
    public String getName() {
        return "/" + name;
    }

    @Override
    public long size() {
        return ivfsObjects.stream().mapToLong(IVFSObject::size).sum();
    }

    @Override
    public Date creationDate() {
        return creationDate;
    }

    @Override
    public boolean addVFSObject(IVFSObject ivfsObject) {
        return ivfsObjects.add(ivfsObject);
    }

    @Override
    public boolean removeVFSObject(IVFSObject ivfsObject) {
        return ivfsObjects.remove(ivfsObject);
    }

    @Override
    public Set<IVFSObject> getVFSObjects() {
        return ivfsObjects;
    }

    @Override
    public void setVFSObjects(Set<IVFSObject> objects) {
        if (objects == null) {
            ivfsObjects = new HashSet<>();
        } else {
            ivfsObjects = objects;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Directory)) return false;

        Directory that = (Directory) o;

        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.toLowerCase().hashCode();
    }
}
