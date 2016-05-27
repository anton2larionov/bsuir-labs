package basic.vfs.impl;

import basic.vfs.interfaces.IDirectory;
import basic.vfs.interfaces.IMountPoint;
import basic.vfs.interfaces.IVFS;

import java.util.HashMap;
import java.util.Map;

/**
 * Простейшая реализация Unix ОС.
 */
public class UnixOS {

    // базовая файловая система
    private final IVFS base;

    // точки монтирования
    private Map<String, IMountPoint> mountPointMap;

    public UnixOS(IVFS base) {
        this.base = base;
        mountPointMap = new HashMap<>();
    }

    // корень базовой файловой системы
    public IDirectory root() {
        return base.root();
    }

    /**
     * Монтирование виртуальной файловой системы.
     *
     * @param parDir родительский каталог точки монтирования
     * @param mp     точка монтирования
     * @param ivfs   виртуальная файловая система для монтирования
     */
    public void mount(IDirectory parDir, IDirectory mp, IVFS ivfs) {

        IMountPoint mountPoint = new MountPoint(mp, ivfs);

        if (parDir.addVFSObject(mountPoint)) {
            mountPointMap.put(mp.getName(), mountPoint);

            System.out.println(ivfs.getName() + " with "
                    + ivfs.charset() + " charset mounted on "
                    + parDir.getName() + mp.getName());
        }
    }

    public IMountPoint getMountPoint(String name) {
        return mountPointMap.get(name);
    }

}
