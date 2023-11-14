package org.example.entities.client;

import org.example.hibernateutils.HibernateUtils;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class ClientCrudService {
    protected final SessionFactory SESSION_FACTORY = HibernateUtils.getInstance().getSessionFactory();
    public Client save(String name) {
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                session.persist(
                    Client.builder()
                    .name(name)
                    .build()
                );
            transaction.commit();
        session.close();
        Client client = getByMaxId();
        if (client == null){
            throw new RuntimeException("Save exception");
        }
        return client;
    }
    public Client update(long id, String newName) {
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                session.merge(
                    Client.builder()
                            .id(id)
                            .name(newName)
                            .build()
                );
            transaction.commit();
        session.close();
        return getById(id);
    }
    public List<Client> getAll(){
        Session session = SESSION_FACTORY.openSession();
        List<Client> list = session
                .createQuery("FROM Client", Client.class)
                .list();
        session.close();
        return list;
    }
    public Client getById(Long id){
        Session session = SESSION_FACTORY.openSession();
        Client client = session.get(Client.class, id);
        session.close();
        return client;
    }

    public Client getByMaxId() {
        Session session = SESSION_FACTORY.openSession();
            Client client = session
                    .createQuery("FROM Client WHERE id = (SELECT MAX(id) FROM Client)", Client.class)
                    .getSingleResult();
        session.close();
        return client;
    }

    public boolean delete(long id){
        Session session = SESSION_FACTORY.openSession();
            Transaction transaction = session.beginTransaction();
                Client toDelete = getById(id);
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
