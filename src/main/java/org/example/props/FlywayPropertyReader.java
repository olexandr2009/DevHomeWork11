package org.example.props;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class FlywayPropertyReader {
    private static final String DEFAULT_PROPERTY_PATH = "flyway.properties";
    private Properties FLYWAY_PROPERTIES;

    public FlywayPropertyReader() {
        this(DEFAULT_PROPERTY_PATH);
    }

    public FlywayPropertyReader(String filePath) {
        try (InputStream input = FlywayPropertyReader.class.getClassLoader()
                .getResourceAsStream(filePath)) {
            FLYWAY_PROPERTIES = new Properties();
            if (input == null) {
                throw new RuntimeException("Sorry, unable to find " + filePath);
            }
            FLYWAY_PROPERTIES.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public String getConnectionURL(){
        String database = FLYWAY_PROPERTIES.getProperty("db.kind");
        return switch (database) {
            case "postgresql" -> getConnectionURLForPostgres();
            case "h2" -> getConnectionURLForH2();
            default -> throw new UnsupportedOperationException("We can't support other databases");
        };
    }

    public String getConnectionURLForPostgres() {
        return new StringBuilder("jdbc:postgresql://")
                .append(FLYWAY_PROPERTIES.getProperty("postgres.db.host"))
                .append(":")
                .append(FLYWAY_PROPERTIES.getProperty("postgres.db.port"))
                .append("/")
                .append(FLYWAY_PROPERTIES.getProperty("postgres.db.database"))
                .append("?currentSchema=public")
                .toString();
    }
    public String getConnectionURLForH2() {
        StringBuilder stringBuilder = new StringBuilder("jdbc:h2:");
        if (FLYWAY_PROPERTIES.getProperty("h2.db.inMemory").equals("true")){
            return stringBuilder
                    .append("mem:")
                    .append(FLYWAY_PROPERTIES.getProperty("h2.db.database"))
                    .toString();
        } else {
            return stringBuilder
                    .append("./")
                    .append(FLYWAY_PROPERTIES.getProperty("h2.db.database"))
                    .toString();
        }
    }

    public String getUsername() {
        return FLYWAY_PROPERTIES.getProperty("db.username");
    }
    public String getPassword() {
        return FLYWAY_PROPERTIES.getProperty("db.password");
    }
}
