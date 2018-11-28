package dev.java.db.daos;

import dev.java.db.model.HRFeedback;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class HRFeedbackDao extends AbstractDao<HRFeedback> {
    public HRFeedbackDao(Session session) {
        super(session);
    }

    @Override
    public List<HRFeedback> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                  boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<HRFeedback> query = criteriaBuilder.createQuery(HRFeedback.class);
        Root<HRFeedback> root = query.from(HRFeedback.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<HRFeedback> typedQuery = getSession().createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemsNumberInPage);
        typedQuery.setMaxResults(itemsNumberInPage);

        return typedQuery.getResultList();
    }

    @Override
    public List<HRFeedback> getFilteredEntitiesPage(String... params) {
        return null;
    }

    @Override
    public HRFeedback getEntityById(long id) {
        return getSession().get(HRFeedback.class, id);
    }
}
