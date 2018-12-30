package dev.java.db.daos;

import dev.java.db.model.Interview;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class InterviewDao extends AbstractDao<Interview> {
    public InterviewDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Interview> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                 boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSessionFactory().getCriteriaBuilder();
        CriteriaQuery<Interview> query = criteriaBuilder.createQuery(Interview.class);
        Root<Interview> root = query.from(Interview.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<Interview> typedQuery = getSessionFactory().getCurrentSession().createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemsNumberInPage);
        typedQuery.setMaxResults(itemsNumberInPage);

        return typedQuery.getResultList();
    }

    @Override
    public List<Interview> getFilteredEntitiesPage(String... params) {
        return null;
    }

    @Override
    public Interview getEntityById(long id) {
        return getSessionFactory().getCurrentSession().get(Interview.class, id);
    }
}
