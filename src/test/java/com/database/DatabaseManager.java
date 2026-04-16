package com.database;

import com.api.utils.ConfigManager;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = ConfigManager.getProperty("DB_URL");
    private static final String DB_USERNAME = ConfigManager.getProperty("DB_USER_NAME");
    private static final String DB_PASSWORD = ConfigManager.getProperty("DB_PASSWORD");
    private static volatile Connection conn;
    private static HikariConfig hikariConfig;
    private static volatile HikariDataSource hikariDataSource;

    public static void createConnection() throws SQLException {
        if (conn == null) {
            synchronized (DatabaseManager.class) {
                if (conn == null) {
                    conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
                    System.out.println(conn);
                }
            }
        }
    }

    private static void instantiateHikariPool() {
        if (hikariDataSource == null) {
            hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(ConfigManager.getProperty("DB_URL"));
            hikariConfig.setUsername(ConfigManager.getProperty("DB_USERNAME"));
            hikariConfig.setPassword(ConfigManager.getProperty("DB_PASSWORD"));
            hikariConfig.setMaximumPoolSize(Integer.parseInt(ConfigManager.getProperty("MAXIMUM_POOL_SIZE")));
            hikariConfig.setMinimumIdle(Integer.parseInt(ConfigManager.getProperty("MINIMUM_IDLE")));
            hikariConfig.setConnectionTimeout(Integer.parseInt(ConfigManager.getProperty("CONNECTION_TIME_OUT")));
            hikariConfig.setIdleTimeout(Integer.parseInt(ConfigManager.getProperty("IDLE_TIME_OUT")));
            hikariConfig.setMaxLifetime(Integer.parseInt(ConfigManager.getProperty("MAX_LIFETIME")));
            hikariConfig.setPoolName(ConfigManager.getProperty("POOL_NAME"));

            try {
                hikariDataSource = new HikariDataSource(hikariConfig);
                Connection ignoredConn = hikariDataSource.getConnection();
                System.out.println("Successfully connected to database");
            } catch (HikariPool.PoolInitializationException | SQLException e) {
                System.err.println("Database connection failed or timed out: " + e.getMessage());
                throw new RuntimeException("Could not initialize database pool within timeout period", e);
            }
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        if(hikariDataSource == null) {
            instantiateHikariPool();
        }
        else if(hikariDataSource.isClosed()) {
            throw new SQLException("HikariDataSource is closed");
        }
        conn = hikariDataSource.getConnection();
        return conn;
    }
}
