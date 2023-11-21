package org.example.entities.ticket;

import org.example.entities.client.ClientCrudService;
import org.example.entities.planet.PlanetCrudService;
import org.example.hibernateutils.HibernateUtils;
import org.example.init.DatabaseInitService;
import org.example.props.FlywayPropertyReader;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class TicketCrudServiceTest {
    private static TicketCrudService ticketCrudService;
    private static FlywayPropertyReader flywayPropertyReader ;
    @BeforeAll
    static void setup(){
        flywayPropertyReader = new FlywayPropertyReader();
        new DatabaseInitService(flywayPropertyReader).initDatabase();
        ticketCrudService = new TicketCrudService();
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
        assertThrows(Exception.class,() -> ticketCrudService.save(9L,"fhghfg","wsedrftgyukjh"));
    }
    @Test
    void testSaveThrowsEx_null(){
        assertThrows(Exception.class,() -> ticketCrudService.save(9L, null, null));
    }
    @Test
    void testSaveDeleteWorksCorrectly(){
        Long id = 12L;
        assertEquals(id, ticketCrudService.save(1L,"MARS","MER").getId());
        ticketCrudService.delete(id);
    }
    @Test
    void testDeleteWorksCorrectly(){
        long id = ticketCrudService.save(1L,"MARS","MER").getId();
        assertTrue(ticketCrudService.delete(id));
    }
    @Test
    void testGetWorksCorrectly(){
        long id = 1L;
        Ticket actual = ticketCrudService.getById(id);
        PlanetCrudService planetCrudService = new PlanetCrudService();
        Ticket expected = Ticket.builder()
                .toPlanetId(planetCrudService.getById("MER"))
                .fromPlanetId(planetCrudService.getById("MARS"))
                .clientId(new ClientCrudService().getById(1L))
                .id(id)
                .build();
        System.out.println(actual);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getClientId(), actual.getClientId());
        assertEquals(expected.getFromPlanetId(), actual.getFromPlanetId());
        assertEquals(expected.getToPlanetId(), actual.getToPlanetId());
    }
    @Test
    void testGetReturns_null(){
        assertNull(ticketCrudService.getById(12342L));
    }
}