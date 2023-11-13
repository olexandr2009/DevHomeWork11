package org.example.hibernateutils;

import lombok.Getter;
import org.example.entities.client.Client;
import org.example.entities.planet.Planet;
import org.example.entities.ticket.Ticket;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

@Getter
public class HibernateUtils {
    private static final HibernateUtils INSTANCE;

    private final SessionFactory sessionFactory;

    static {
        INSTANCE = new HibernateUtils();
    }

    private HibernateUtils() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Client.class)
                .addAnnotatedClass(Ticket.class)
                .addAnnotatedClass(Planet.class)
                .buildSessionFactory();
    }

    public static HibernateUtils getInstance() {
        return INSTANCE;
    }

    public void close() {
        sessionFactory.close();
    }
}
