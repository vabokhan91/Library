package by.epam.bokhan.dao.connectionpool;

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
//    private static final Logger LOGGER = LogManager.getLogger();
    private static final int POOL_SIZE = 10;
    private static Lock lock = new ReentrantLock();
    private BlockingQueue<T> connectionQueue;
    private static ConnectionPool instance;
    private static AtomicBoolean isConnectionPoolCreated = new AtomicBoolean(false);

    private ConnectionPool() throws ClassNotFoundException {
        connectionQueue = new ArrayBlockingQueue<>(POOL_SIZE);
        initConnectionPool();
    }

    public static ConnectionPool getInstance() {
        if (!isConnectionPoolCreated.get()) {
            lock.lock();
            try {
                if (instance == null) {
                    try {
                        instance = new ConnectionPool();
                        isConnectionPoolCreated.set(true);
                    } catch (ClassNotFoundException e) {
//                        LOGGER.log(Level.FATAL, String.format("Connection pool was not created. Reason : %s", e));
                    }
                }
            } finally {
                lock.unlock();
            }
        }
        return instance;
    }

    private void initConnectionPool() throws ClassNotFoundException {
        for (int i = 0; i < POOL_SIZE; i++) {
            Class.forName("com.mysql.jdbc.Driver");
            createConnectionAndAddToPool();
        }
        if (!isAllConnectionsCreated()) {
            tryToRecreateConnections();
            if (connectionQueue.size() != POOL_SIZE) {
                System.out.println("Connection pool is not full. Availiable connections: " + connectionQueue.size());
            } else {
//                LOGGER.log(Level.INFO, String.format("All connections were created successfully. Connection pool size: %s connections", connectionQueue.size()));
            }
        } else {
//            LOGGER.log(Level.INFO, String.format("All connections were created successfully. Connection pool size: %s connections", connectionQueue.size()));
        }

    }

    private void tryToRecreateConnections() {
        int difference = POOL_SIZE - connectionQueue.size();
        for (int i = 0; i < difference; i++) {
            createConnectionAndAddToPool();
        }
    }

    private void createConnectionAndAddToPool() {
        T connection = null;
        try {
            connection = (T) new ConnectionCreator().getConnection();
        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, String.format("Connection was not created. Reason : %s", e.getMessage()));
        }
        try {
            connectionQueue.put(connection);
            connection.setAutoCommit(true);
        } catch (InterruptedException e) {
//            LOGGER.log(Level.ERROR, String.format("Connection was not added to pool. Reason : %s", e.getMessage()));
        } catch (SQLException e) {
//            LOGGER.log(Level.ERROR, String.format("AutoCommit was not set : %s", e.getMessage()));
        }
    }

    private boolean isAllConnectionsCreated() {
        return connectionQueue.size() < POOL_SIZE;
    }

    public T getConnection() {
        T connection = null;
        try {
            connection = connectionQueue.take();
        } catch (InterruptedException e) {
            System.out.println("Can not take connection");
            e.printStackTrace();
        }
        return connection;
    }

    public void releaseConnection(T connection) {
        try {
            if (connection.isValid(0)) {
                if (!connectionQueue.offer(connection)) {
                    throw new SQLException("Can not retrive connection to pool. It is already full");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
