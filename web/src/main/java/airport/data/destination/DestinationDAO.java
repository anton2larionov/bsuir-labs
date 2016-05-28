package airport.data.destination;

import java.util.Collection;

/**
 * DAO для Destination entity.
 */
public interface DestinationDAO {

    void add(Destination object);

    Collection<Destination> getAll();

    void delete(String string);

}
