package dev.java.db.daos;

import dev.java.db.model.DevFeedback;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class DevFeedbackDao extends AbstractDao<DevFeedback> {
    public DevFeedbackDao(Session session) {
        super(session);
    }

    @Override
    public List<DevFeedback> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                  boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<DevFeedback> query = criteriaBuilder.createQuery(DevFeedback.class);
        Root<DevFeedback> root = query.from(DevFeedback.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<DevFeedback> typedQuery = getSession().createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemsNumberInPage);
        typedQuery.setMaxResults(itemsNumberInPage);

        return typedQuery.getResultList();
    }

    @Override
    public List<DevFeedback> getFilteredEntitiesPage(String... params) {
        return null;
    }

    @Override
    public DevFeedback getEntityById(long id) {
        return getSession().get(DevFeedback.class, id);
    }
}
