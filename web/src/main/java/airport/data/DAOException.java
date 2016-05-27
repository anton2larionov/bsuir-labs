package airport.data;

import java.sql.SQLException;

/**
 * Исключение при работе с DAO.
 */
public final class DAOException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public DAOException(String message) {
		super(message);
	}
	
	public DAOException(SQLException e) {
		super(e);
	}
	
	public DAOException(String message, SQLException e) {
		super(message, e);
	}
	
}
