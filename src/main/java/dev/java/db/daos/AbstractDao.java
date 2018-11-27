package dev.java.db.daos;

import dev.java.db.model.AbstractEntity;
import org.hibernate.Session;

import java.util.List;

public abstract class AbstractDao<T extends AbstractEntity> {
    private Session session;

    public AbstractDao(Session session) {
        this.session = session;
    }

    public abstract List<T> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                  boolean order, int itemsNumberInPage);

    public abstract List<T> getFilteredEntitiesPage(String... params);

    public boolean createEntity(T entity) {
        try {
            session.beginTransaction().begin();
            session.save(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    public boolean updateEntity(T entity) {
        try {
            if (!session.beginTransaction().isActive()) {
                session.beginTransaction().begin();
            }
            session.update(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    public boolean deleteEntity(T entity) {
        try {
            session.beginTransaction().begin();
            session.delete(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    public boolean deleteEntityById(long id) {
        try {
            session.beginTransaction().begin();
            T entity = getEntityById(id);
            session.delete(entity);
            session.getTransaction().commit();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return false;
    }

    public abstract T getEntityById(long id);


    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
