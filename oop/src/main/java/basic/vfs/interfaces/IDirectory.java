package basic.vfs.interfaces;

import java.util.Set;
import java.util.function.Consumer;

/**
 * Интерфейс содержит методы доступа к
 * конкретному каталогу виртуальной файловой системы.
 */
public interface IDirectory extends IVFSObject {

    // добавить объект в каталог
    boolean addVFSObject(IVFSObject ivfsObject);

    // удалить объект из каталога
    boolean removeVFSObject(IVFSObject ivfsObject);

    // получить множество объектов из каталога
    Set<IVFSObject> getVFSObjects();

    // присвоить каталогу множество объектов
    void setVFSObjects(Set<IVFSObject> objects);

    // посетить дерево каталогов
    default void visitAll(Consumer<IVFSObject> ivfsObjectConsumer) {
        ivfsObjectConsumer.accept(this);
        getVFSObjects().stream()
                .sorted((o1, o2) -> o1.getName().compareTo(o2.getName()))
                .forEach(ivfsObject -> {

                    if (ivfsObject instanceof IDirectory) {
                        ((IDirectory) ivfsObject).visitAll(ivfsObjectConsumer);
                    } else if (ivfsObject instanceof IMountPoint) {
                        ((IMountPoint) ivfsObject).root().visitAll(ivfsObjectConsumer);
                    } else {
                        ivfsObjectConsumer.accept(ivfsObject);
                    }
                });
    }

}
