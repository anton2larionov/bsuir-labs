package jdbcxml.repository;

import jdbcxml.data.Medicament;

import java.util.Collection;

/**
 * Приемник (consumer) Medicament.
 */
public interface MedicamentSink {

    void putAll(Collection<Medicament> medicaments);

}
