package jdbcxml.repository.jdbc;

import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import static jdbcxml.repository.jdbc.Config.*;

/**
 * Пул потоков для подключения к БД.
 */
public class ConnectionPool implements AutoCloseable {

    private static final Logger logger = Logger.getLogger(ConnectionPool.class);

    private static final String SQL_EX = "SQLException in ConnectionPool";
    private static final String DRIVER_EX = "Can't find database driver class";
    private static final String CLOSE_EX = "Error closing the connection";
    private static final String CONN_EX = "Error connecting to the data source";
    private static final String CONN_MSG = "Connect to the data source";
    private static final String INIT_MSG = "Init connection queue";
    private static final String EXIT_MSG = "Clear connection queue";

    private BlockingQueue<PooledConnection> connectionQueue;
    private BlockingQueue<PooledConnection> givenAwayConQueue;

    public ConnectionPool() throws SQLException {
        ConfigManager configManager = ConfigManager.getInstance();
        String driverName = configManager.getString(DRIVER);
        String url = configManager.getString(URL);
        String user = configManager.getString(USER);
        String password = configManager.getString(PASSWORD);
        int poolSize;
        try {
            poolSize = configManager.getInt(POOLSIZE);
        } catch (Exception e) {
            poolSize = 5; // по умолчанию
        }
        try {
            Class.forName(driverName);
            givenAwayConQueue = new ArrayBlockingQueue<>(poolSize);
            connectionQueue = new ArrayBlockingQueue<>(poolSize);
            for (int i = 0; i < poolSize; i++) {
                Connection connection = DriverManager.getConnection(url, user, password);
                connectionQueue.offer(new PooledConnection(
                        connection, connectionQueue, givenAwayConQueue));
            }
            logger.info(INIT_MSG);
        } catch (SQLException e) {
            logger.error(SQL_EX, e);
            throw e;
        } catch (ClassNotFoundException e) {
            logger.error(DRIVER_EX, e);
            throw new RuntimeException(DRIVER_EX, e);
        }
    }

    @Override
    public void close() throws SQLException {
        clearConnectionQueue();
        logger.info(EXIT_MSG);
    }

    private void clearConnectionQueue() throws SQLException {
        try {
            closeConnectionsQueue(givenAwayConQueue);
            closeConnectionsQueue(connectionQueue);
        } catch (SQLException e) {
            logger.error(CLOSE_EX, e);
            throw e;
        }
    }

    public Connection takeConnection() {
        PooledConnection pooledConnection;
        try {
            pooledConnection = connectionQueue.take();
            givenAwayConQueue.put(pooledConnection);
        } catch (InterruptedException e) {
            logger.error(CONN_EX, e);
            throw new RuntimeException(CONN_EX, e);
        }
        logger.info(CONN_MSG);
        return pooledConnection;
    }

    private void closeConnectionsQueue(BlockingQueue<PooledConnection> queue) throws SQLException {
        PooledConnection pooledConnection;
        while ((pooledConnection = queue.poll()) != null) {
            if (!pooledConnection.isClosed() && !pooledConnection.getAutoCommit()) {
                pooledConnection.commit();
                pooledConnection.reallyClose();
            }
        }
    }
}
