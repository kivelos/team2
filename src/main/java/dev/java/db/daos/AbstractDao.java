package dev.java.db.daos;

import dev.java.db.model.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public abstract class AbstractDao<T extends AbstractEntity> {
    private SessionFactory sessionFactory;

    public AbstractDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public abstract List<T> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                  boolean order, int itemsNumberInPage);

    public abstract List<T> getFilteredEntitiesPage(String... params);

    public boolean createEntity(T entity) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(entity);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateEntity(T entity) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.update(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEntity(T entity) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.delete(entity);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteEntityById(long id) {
        try {
            Session session = sessionFactory.getCurrentSession();
            T entity = getEntityById(id);
            session.delete(entity);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public abstract T getEntityById(long id);


    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
