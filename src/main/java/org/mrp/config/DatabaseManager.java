package org.mrp.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static final String ENV_KEY_DB_URL = "DB_URL";
    private static final String ENV_KEY_DB_USER = "DB_USER";
    private static final String ENV_KEY_DB_PASS = "DB_PASS";
    private static final String ENV_DIR = "./src/main/resources";
    private static final String ENV_FILE = "postgres.env";

    private final String dbUrl;
    private final String dbUser;
    private final String dbPass;

    public DatabaseManager() {
        Dotenv dotenv = Dotenv.configure().directory(ENV_DIR).filename(ENV_FILE).load();
        this.dbUrl = dotenv.get(ENV_KEY_DB_URL);
        this.dbUser = dotenv.get(ENV_KEY_DB_USER);
        this.dbPass = dotenv.get(ENV_KEY_DB_PASS);
    }

    public void runMigrations() {
        Flyway flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPass).load();
        flyway.migrate();
    }

    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl, dbUser, dbPass);
    }
}
