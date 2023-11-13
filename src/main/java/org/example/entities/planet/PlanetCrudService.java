package org.example.entities.planet;


import org.example.hibernateutils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class PlanetCrudService {
    private final SessionFactory SESSION_FACTORY = HibernateUtils.getInstance().getSessionFactory();

    public Planet save(String id, String name) {
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                session.persist(
                    Planet.builder()
                            .id(id)
                            .name(name)
                            .build()
                    );
            transaction.commit();
        session.close();
        Planet planet = getById(id);
        if (planet == null){
            throw new RuntimeException("Save exception");
        }
        return planet;
    }
    public Planet update(String id, String newName) {
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                session.merge(
                    Planet.builder()
                            .id(id)
                            .name(newName)
                            .build()
                    );
            transaction.commit();
        session.close();
        return getById(id);
    }
    public List<Planet> getAll(){
        Session session = SESSION_FACTORY.openSession();
        List<Planet> list = session
                .createQuery("FROM Planet", Planet.class)
                .list();
        session.close();
        return list;
    }

    public Planet getById(String id){
        Session session = SESSION_FACTORY.openSession();
        Planet planet = session
                .createQuery("FROM Client WHERE id = :id", Planet.class)
                .setParameter("id", id)
                .getSingleResult();
        session.close();
        return planet;
    }
    public boolean delete(String id){
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                Planet toDelete = getById(id);
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
