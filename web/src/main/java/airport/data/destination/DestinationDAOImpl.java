package airport.data.destination;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import airport.data.DAOException;

/**
 * Реализация на основе JDBC.
 */
public class DestinationDAOImpl implements DestinationDAO {
	
	private static final Logger logger = Logger.getLogger(DestinationDAOImpl.class);

	private static final String SELECT_ALL = "SELECT * FROM destination";
	private static final String INSERT = "INSERT INTO destination(city) VALUES(?)";
	private static final String DELETE = "DELETE FROM destination WHERE city = ?";

	private DataSource dataSource;
	
	public DestinationDAOImpl(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	@Override
	public void add(Destination object) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(INSERT)) {
			
			statement.setString(1, object.getName());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			logger.error(e);
			if (e.getSQLState().startsWith("23")) {
				throw new DAOException("This destination already exists.");
			}
			throw new DAOException(e);
		}
	}

	@Override
	public Collection<Destination> getAll() {
		Collection<Destination> ds = new ArrayList<>();

		try (Connection connection = dataSource.getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(SELECT_ALL)) {
			
			while (resultSet.next()) {
				ds.add(new Destination(resultSet.getString("city")));
			}
		} catch (SQLException e) {
			logger.error(e);
			throw new DAOException(e);
		}
		return ds;
	}

	@Override
	public void delete(String string) {
		try (Connection connection = dataSource.getConnection();
				PreparedStatement statement = connection.prepareStatement(DELETE)) {
			
			statement.setString(1, string);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			logger.error(e);
			throw new DAOException(e);
		}
	}
}
