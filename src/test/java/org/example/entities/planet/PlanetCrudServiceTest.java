package org.example.entities.planet;

import org.example.entities.client.ClientCrudService;
import org.example.hibernateutils.HibernateUtils;
import org.example.init.DatabaseInitService;
import org.example.props.FlywayPropertyReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlanetCrudServiceTest {
    private static PlanetCrudService planetCrudService;
    private static FlywayPropertyReader flywayPropertyReader ;
    @BeforeAll
    static void setup(){
        flywayPropertyReader = new FlywayPropertyReader();
        new DatabaseInitService(flywayPropertyReader).initDatabase();
        planetCrudService = new PlanetCrudService();
    }
    @AfterAll
    static void dropConnection() throws Exception{
        HibernateUtils.getInstance().close();

        try (Connection shutdown = DriverManager.getConnection(flywayPropertyReader.getConnectionURL(),
                flywayPropertyReader.getUsername(),
                flywayPropertyReader.getPassword())
        ) {
            try(Statement statement = shutdown.createStatement()){
                statement.executeUpdate("SHUTDOWN");
            }
        }
    }
    @Test
    void testSaveThrowsEx_f(){
        assertThrows(Exception.class,() -> planetCrudService.save("f","f"));
    }
    @Test
    void testSaveThrowsEx_null(){
        assertThrows(Exception.class,() -> planetCrudService.save(null,null));
    }
    @Test
    void testSaveDeleteWorksCorrectly(){
        String id = "URAN";
        assertEquals(id, planetCrudService.save(id,"Nazar").getId());
        planetCrudService.delete(id);
    }
    @Test
    void testDeleteWorksCorrectly(){
        String id = planetCrudService.save("URAN","Uran").getId();
        assertTrue(planetCrudService.delete(id));
    }
    @Test
    void testUpdateWorksCorrectly(){
        String id = "MARS";
        Planet beforeUpdate = planetCrudService.getById(id);
        Planet updated = planetCrudService.update(id, "Mars");
        planetCrudService.update(id, beforeUpdate.getName());
        assertEquals(new Planet(id,"Mars"), updated);
    }
    @Test
    void testGetWorksCorrectly(){
        String id = "MARS";
        assertEquals(new Planet(id,"Mars"), planetCrudService.getById(id));
    }
    @Test
    void testGetReturns_null(){
        assertNull(planetCrudService.getById("FFFFFFFF"));
    }
    @Test
    void testGetAllWorksCorrectly(){
        List<Planet> planets = List.of(
                new Planet("VEN","Venera"),
                new Planet("MARS","Mars"),
                new Planet("MER","Mercury"),
                new Planet("SAT","Saturn"),
                new Planet("NEP","Neptune")
        );
        assertEquals(planets, planetCrudService.getAll());
    }
}