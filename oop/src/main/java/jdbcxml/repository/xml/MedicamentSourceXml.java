package jdbcxml.repository.xml;

import jdbcxml.repository.MedicamentSource;

import java.nio.file.Path;

/**
 * Абстрактный источник (producer) Medicament на основе XML-файла.
 */
public abstract class MedicamentSourceXml implements MedicamentSource {

    private final Path path;

    protected MedicamentSourceXml(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
