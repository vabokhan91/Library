package by.epam.bokhan.pool;

import by.epam.bokhan.exception.ConnectionPoolException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by vbokh on 14.07.2017.
 */
public class ConnectionPool<T extends Connection> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int POOL_SIZE = 10;
    private static Lock lock = new ReentrantLock();
    private BlockingQueue<ProxyConnection> connectionQueue;
    private static ConnectionPool instance;
    private static AtomicBoolean isConnectionPoolCreated = new AtomicBoolean(false);

    private ConnectionPool() throws ConnectionPoolException {
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        try {
            initConnectionPool();
        } catch (ClassNotFoundException e) {
            LOGGER.log(Level.FATAL, String.format("Connection pool was not created. Reason : %s", e.getMessage()));
            throw new ConnectionPoolException("Connection pool was not created.", e);
        }
    }

    public static ConnectionPool getInstance() {
        if (!isConnectionPoolCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    instance = new ConnectionPool();
                    isConnectionPoolCreated.set(true);
                }
            } catch (ConnectionPoolException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private void initConnectionPool() throws ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        for (int i = 0; i < POOL_SIZE; i++) {
            createConnectionAndAddToPool();
        }
        if (!isAllConnectionsCreated()) {
            tryToRecreateConnections();
            if (connectionQueue.size() != POOL_SIZE) {
                LOGGER.log(Level.INFO, "Connection pool is not full. Available connections: " + connectionQueue.size());
            } else {
                LOGGER.log(Level.INFO, String.format("All connections were created successfully. Connection pool size: %s connections", connectionQueue.size()));
            }
        } else {
            LOGGER.log(Level.INFO, String.format("All connections were created successfully. Connection pool size: %s connections", connectionQueue.size()));
        }

    }

    private void createConnectionAndAddToPool() {
        try {
            ProxyConnection connection = ConnectionCreator.getConnection();
            connectionQueue.put(connection);
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, String.format("Connection was not created. Reason : %s", e.getMessage()));
        } catch (InterruptedException e) {
            LOGGER.log(Level.ERROR, String.format("Connection was not added to pool. Reason : %s", e.getMessage()));
        }
    }

    private void tryToRecreateConnections() {
        int difference = POOL_SIZE - connectionQueue.size();
        for (int i = 0; i < difference; i++) {
            createConnectionAndAddToPool();
        }
    }

    private boolean isAllConnectionsCreated() {
        return connectionQueue.size() < POOL_SIZE;
    }

    public ProxyConnection getConnection() {
        ProxyConnection connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            System.out.println("Can not take connection");
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(ProxyConnection connection) {
        try {
            if (connection.isValid(0)) {
                if (!connectionQueue.offer(connection)) {
                    throw new SQLException("Can not retrive connection to pool. It is already full");
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.ERROR, e.getMessage());
        }
    }

    public void destroyConnections() {
        for (int i = 0; i < connectionQueue.size(); i++) {
            try {
                ProxyConnection connection = connectionQueue.take();
                connection.closeConnection();
            } catch (InterruptedException e) {
                LOGGER.log(Level.ERROR, String.format("Can not take connection from pool. Reason : %s", e.getMessage()));
            } catch (SQLException e) {
                LOGGER.log(Level.ERROR, String.format("Can not close connection. Reason : %s", e.getMessage()));
            }
        }
    }
}
