package org.mrp.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.flywaydb.core.Flyway;

public class DatabaseManager {
    private static final String ENV_KEY_DB_URL = "DB_URL";
    private static final String ENV_KEY_DB_USER = "DB_USER";
    private static final String ENV_KEY_DB_PASS = "DB_PASS";
    private static final String ENV_DIR = "./src/main/resources";
    private static final String ENV_FILE = "postgres.env";


    public void runMigrations() {
        Dotenv dotenv = Dotenv.configure().directory(ENV_DIR).filename(ENV_FILE).load();

        final String dbUrl = dotenv.get(ENV_KEY_DB_URL);
        final String dbUser = dotenv.get(ENV_KEY_DB_USER);
        final String dbPass = dotenv.get(ENV_KEY_DB_PASS);

        Flyway flyway = Flyway.configure().dataSource(dbUrl, dbUser, dbPass).load();
        flyway.migrate();
    }
}
