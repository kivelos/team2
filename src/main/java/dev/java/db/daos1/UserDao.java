package dev.java.db.daos1;

import dev.java.db.model1.User;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class UserDao extends AbstractDao<User> {
    public UserDao(Session session) {
        super(session);
    }

    @Override
    public List<User> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                 boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<User> query = criteriaBuilder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<User> typedQuery = getSession().createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemsNumberInPage);
        typedQuery.setMaxResults(itemsNumberInPage);

        return typedQuery.getResultList();
    }

    @Override
    public List<User> getFilteredEntitiesPage(String... params) {
        return null;
    }

    @Override
    public User getEntityById(long id) {
        return getSession().get(User.class, id);
    }
}
