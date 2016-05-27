package jdbcxml.repository;

import jdbcxml.data.Medicament;

import java.util.Collection;

/**
 * Источник (producer) Medicament.
 */
public interface MedicamentSource {

    Collection<Medicament> getAll();

}
