package org.example.entities.ticket;

import org.example.entities.client.Client;
import org.example.entities.client.ClientCrudService;
import org.example.entities.planet.Planet;
import org.example.entities.planet.PlanetCrudService;
import org.example.hibernateutils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TicketCrudService {
    private final SessionFactory SESSION_FACTORY = HibernateUtils.getInstance().getSessionFactory();
    private static final ClientCrudService CLIENT_CRUD_SERVICE = new ClientCrudService();
    private static final PlanetCrudService PLANET_CRUD_SERVICE = new PlanetCrudService();
    public Ticket save(Long clientId, String fromPlanet, String toPlanet) {
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                session.persist(
                        Ticket.builder()
                                .clientId(CLIENT_CRUD_SERVICE.getById(clientId))
                                .fromPlanetId(PLANET_CRUD_SERVICE.getById(fromPlanet))
                                .toPlanetId(PLANET_CRUD_SERVICE.getById(toPlanet))
                                .build()
                );
            transaction.commit();
        session.close();
        Ticket ticket = getByMaxId();
        if (ticket == null){
            throw new RuntimeException("Save exception");
        }
        return ticket;
    }
    public Ticket save(Client client, Planet fromPlanet, Planet toPlanet) {
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                session.persist(
                        Ticket.builder()
                                .clientId(client)
                                .fromPlanetId(fromPlanet)
                                .toPlanetId(toPlanet)
                                .build()
                );
            transaction.commit();
        session.close();
        Ticket ticket = getByMaxId();
        if (ticket == null){
            throw new RuntimeException("Save exception");
        }
        return ticket;
    }
    public List<Ticket> getAll(){
        Session session = SESSION_FACTORY.openSession();
            List<Ticket> list = session
                    .createQuery("FROM Ticket", Ticket.class)
                    .list();
        session.close();
        return list;
    }
    public Ticket getById(Long id){
       try (Session session = SESSION_FACTORY.openSession()){
           Ticket ticket = session.get(Ticket.class, id);
           if (ticket == null){
               return null;
           }
           System.out.print(ticket.getClientId());
           System.out.print(ticket.getFromPlanetId());
           System.out.println(ticket.getToPlanetId());
           session.close();
           return ticket;
       }
    }

    public Ticket getByMaxId() {
        Session session = SESSION_FACTORY.openSession();
            Ticket ticket = session
                    .createQuery("FROM Ticket WHERE id = (SELECT MAX(id) FROM Ticket)", Ticket.class)
                    .getSingleResult();
            session.close();
        return ticket;
    }

    public boolean delete(long id){
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                Ticket toDelete = getById(id);
                if (toDelete == null){
                    return false;
                }
                session.evict(toDelete);
                session.remove(toDelete);
            transaction.commit();
        session.close();
        return true;
    }
}
