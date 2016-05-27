package jdbcxml.repository.xml;

import jdbcxml.repository.MedicamentSink;

import java.nio.file.Path;

/**
 * Абстрактный приемник (writer) Medicament на основе XML-файла.
 */
public abstract class MedicamentSinkXml implements MedicamentSink {

    private final Path path;

    protected MedicamentSinkXml(Path path) {
        this.path = path;
    }

    public Path getPath() {
        return path;
    }
}
