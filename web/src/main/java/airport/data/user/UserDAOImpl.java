package airport.data.user;

import airport.data.DAOException;
import airport.data.destination.Destination;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация на основе JDBC.
 */
public class UserDAOImpl implements UserDAO {

    private static final Logger logger = Logger.getLogger(UserDAOImpl.class);

    private static final String SELECT_USER =
            "SELECT a.login, a.password, r.role " +
                    "FROM auth a, login_role r WHERE a.login = ? AND a.login = r.login";

    private static final String INSERT_AUTH =
            "INSERT INTO auth(login, password) VALUES(?, ?)";

    private static final String INSERT_LOGIN_ROLE =
            "INSERT INTO login_role(login, role) VALUES(?, ?)";

    private static final String INSERT_LOGIN_DEST =
            "INSERT INTO history(login, city) VALUES(?, ?)";

    private DataSource dataSource;

    public UserDAOImpl(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Pair<User, Role> getUserRole(String login) {
        Pair<User, Role> pair;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SELECT_USER)) {

            statement.setString(1, login);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setLogin(resultSet.getString("login"));
                user.setPassword(resultSet.getString("password"));

                pair = Pair.of(user,
                        Role.valueOf(resultSet.getString("role").toUpperCase()));
            } else {
                throw new DAOException("User with this login is not found.");
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
        return pair;
    }

    @Override
    public void addUserRole(Pair<User, Role> pair) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement st1 = connection.prepareStatement(INSERT_AUTH);
             PreparedStatement st2 = connection.prepareStatement(INSERT_LOGIN_ROLE)) {

            User user = pair.getLeft();
            st1.setString(1, user.getLogin());
            st1.setString(2, user.getPassword());
            st1.executeUpdate();

            Role role = pair.getRight();
            st2.setString(1, user.getLogin());
            st2.setString(2, role.toString());
            st2.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            if (e.getSQLState().startsWith("23")) {
                throw new DAOException("This user already exists.");
            }
            throw new DAOException(e);
        }
    }

    @Override
    public void addUserDestination(Pair<User, Destination> pair) {
        try (Connection connection = dataSource.getConnection();
             PreparedStatement st1 = connection.prepareStatement(INSERT_LOGIN_DEST)) {

            User user = pair.getLeft();
            Destination dest = pair.getRight();
            st1.setString(1, user.getLogin());
            st1.setString(2, dest.getName());
            st1.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
            throw new DAOException(e);
        }
    }
}
