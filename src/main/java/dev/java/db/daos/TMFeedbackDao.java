package dev.java.db.daos;

import dev.java.db.model.TMFeedback;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class TMFeedbackDao extends AbstractDao<TMFeedback> {
    public TMFeedbackDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<TMFeedback> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                  boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSessionFactory().getCriteriaBuilder();
        CriteriaQuery<TMFeedback> query = criteriaBuilder.createQuery(TMFeedback.class);
        Root<TMFeedback> root = query.from(TMFeedback.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<TMFeedback> typedQuery = getSessionFactory().getCurrentSession().createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemsNumberInPage);
        typedQuery.setMaxResults(itemsNumberInPage);

        return typedQuery.getResultList();
    }

    @Override
    public List<TMFeedback> getFilteredEntitiesPage(String... params) {
        return null;
    }

    @Override
    public TMFeedback getEntityById(long id) {
        return getSessionFactory().getCurrentSession().get(TMFeedback.class, id);
    }
}
