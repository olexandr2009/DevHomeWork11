package org.example.init;


import org.example.props.FlywayPropertyReader;
import org.flywaydb.core.Flyway;

public class DatabaseInitService {
    private final FlywayPropertyReader PROPERTY_READER;
    public DatabaseInitService(){
        this(new FlywayPropertyReader());
    }
    public DatabaseInitService(FlywayPropertyReader propertyReader){
        PROPERTY_READER = propertyReader;
    }
    public void initDatabase(){
        Flyway flyway = Flyway.configure().dataSource(
                        PROPERTY_READER.getConnectionURL(),
                        PROPERTY_READER.getUsername(),
                        PROPERTY_READER.getPassword())
                .load();
        flyway.migrate();
    }
}
