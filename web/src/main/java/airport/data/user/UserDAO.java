package airport.data.user;

import org.apache.commons.lang3.tuple.Pair;

import airport.data.destination.Destination;

/**
 * DAO для User entity и 
 * отношений с Role (one to many) и Destination (many to many).
 */
public interface UserDAO {
	void addUserRole(Pair<User, Role> pair);
	void addUserDestination(Pair<User, Destination> pair);
	Pair<User, Role> getUserRole(String login);
}
