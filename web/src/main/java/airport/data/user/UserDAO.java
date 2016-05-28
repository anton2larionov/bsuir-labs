package airport.data.user;

import airport.data.destination.Destination;
import org.apache.commons.lang3.tuple.Pair;

/**
 * DAO для User entity и
 * отношений с Role (one to many) и Destination (many to many).
 */
public interface UserDAO {
    void addUserRole(Pair<User, Role> pair);

    void addUserDestination(Pair<User, Destination> pair);

    Pair<User, Role> getUserRole(String login);
}
