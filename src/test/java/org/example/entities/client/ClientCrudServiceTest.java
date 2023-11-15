package org.example.entities.client;

import org.example.hibernateutils.HibernateUtils;
import org.example.init.DatabaseInitService;
import org.example.props.FlywayPropertyReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ClientCrudServiceTest {
    private static ClientCrudService clientCrudService;
    private static FlywayPropertyReader flywayPropertyReader ;
    @BeforeAll
    static void setup(){
        flywayPropertyReader = new FlywayPropertyReader();
        new DatabaseInitService(flywayPropertyReader).initDatabase();
        clientCrudService = new ClientCrudService();
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
    void testSaveThrowsEx_F(){
        assertThrows(Exception.class,() -> clientCrudService.save("F"));
    }
    @Test
    void testSaveThrowsEx_null(){
        assertThrows(Exception.class,() -> clientCrudService.save(null));
    }
    @Test
    void testSaveDeleteWorksCorrectly(){
        Long id = 11L;
        assertEquals(id, clientCrudService.save("Nazar").getId());
        clientCrudService.delete(id);
    }
    @Test
    void testDeleteWorksCorrectly(){
        long id = clientCrudService.save("Valera").getId();
        assertTrue(clientCrudService.delete(id));
    }
    @Test
    void testUpdateWorksCorrectly(){
        long id = 1L;
        Client beforeUpdate = clientCrudService.getById(id);
        Client updated = clientCrudService.update(id, "Volodymyr");
        clientCrudService.update(id, beforeUpdate.getName());
        assertEquals(new Client(id,"Volodymyr"), updated);
    }
    @Test
    void testGetWorksCorrectly(){
        long id = 1L;
        assertEquals(new Client(id,"Bogdan"),clientCrudService.getById(id));
    }
    @Test
    void testGetReturns_null(){
        assertNull(clientCrudService.getById(12342L));
    }
    @Test
    void testGetAllWorksCorrectly(){
        List<Client> clients = List.of(
                new Client(1,"Bogdan"),
                new Client(2,"Volodymyr"),
                new Client(3,"Roman"),
                new Client(4,"Olexandr"),
                new Client(5,"Maksym"),
                new Client(6,"Roman"),
                new Client(7,"Anatoliy"),
                new Client(8,"Nazar"),
                new Client(9,"Pavlo"),
                new Client(10,"Nazar")
        );
        assertEquals(clients, clientCrudService.getAll());
    }
}