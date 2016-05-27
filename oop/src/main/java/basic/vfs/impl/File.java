package basic.vfs.impl;

import basic.vfs.interfaces.IFile;

import java.util.Date;

/**
 * Абстрактный файл Native - виртуальной файловой системы.
 */
public abstract class File implements IFile {

    private final Date creationDate;
    private String name;

    File(String name) {
        if (name != null && name.trim().matches("[\\w .]+")) {
            this.name = name;
        } else {
            this.name = "new_file";
        }

        creationDate = new Date();
    }

    @Override
    public Date creationDate() {
        return creationDate;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof File)) return false;

        File that = (File) o;

        return getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return getName().toLowerCase().hashCode();
    }
}
