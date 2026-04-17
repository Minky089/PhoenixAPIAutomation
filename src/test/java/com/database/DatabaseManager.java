package com.database;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.pool.HikariPool;

import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String DB_URL = EnvUtil.getValue("DB_URI");
    private static final String DB_USERNAME = EnvUtil.getValue("DB_USERNAME");
    private static final String DB_PASSWORD = EnvUtil.getValue("DB_PASSWORD");
    private static volatile Connection conn;
    private static volatile HikariDataSource hikariDataSource;

    private static void instantiateHikariPool() {
        if (hikariDataSource == null) {
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setJdbcUrl(DB_URL);
            hikariConfig.setUsername(DB_USERNAME);
            hikariConfig.setPassword(DB_PASSWORD);
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
