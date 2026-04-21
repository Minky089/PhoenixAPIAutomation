package com.database;

import com.api.utils.ConfigManager;
import com.api.utils.EnvUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import io.qameta.allure.Step;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;

@Log4j2
public class DatabaseManager {
    private static final String DB_URL = EnvUtil.getValue("DB_URI");
    private static final String DB_USERNAME = EnvUtil.getValue("DB_USERNAME");
    private static final String DB_PASSWORD = EnvUtil.getValue("DB_PASSWORD");
    private static volatile Connection conn;
    private static volatile HikariDataSource hikariDataSource;

    @Step("Initializing the Database connection pool")
    private static void instantiateHikariPool() {
        if (hikariDataSource == null) {
            log.warn("Database Connection is not available yet, creating Hikari Datasource");
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
            hikariDataSource = new HikariDataSource(hikariConfig);
            log.info("Hikari Datasource created");
        }
    }

    @Step("Getting the Database Connection")
    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        if(hikariDataSource == null) {
            log.info("Initializing Database connection with Hikari CP");
            instantiateHikariPool();
        }
        else if(hikariDataSource.isClosed()) {
            log.error("HIKARI DATA SOURCE IS CLOSED");
            throw new SQLException("HIKARI DATA SOURCE IS CLOSED");
        }
        conn = hikariDataSource.getConnection();
        return conn;
    }
}
