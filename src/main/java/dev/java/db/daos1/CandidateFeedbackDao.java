package dev.java.db.daos1;

import dev.java.db.model1.CandidateFeedback;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CandidateFeedbackDao extends AbstractDao<CandidateFeedback>{
    public CandidateFeedbackDao(Session session) {
        super(session);
    }

    @Override
    public List<CandidateFeedback> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                         boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<CandidateFeedback> query = criteriaBuilder.createQuery(CandidateFeedback.class);
        Root<CandidateFeedback> root = query.from(CandidateFeedback.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<CandidateFeedback> typedQuery = getSession().createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemsNumberInPage);
        typedQuery.setMaxResults(itemsNumberInPage);

        return typedQuery.getResultList();
    }

    @Override
    public List<CandidateFeedback> getFilteredEntitiesPage(String... params) {
        return null;
    }

    @Override
    public CandidateFeedback getEntityById(long id) {
        return getSession().get(CandidateFeedback.class, id);
    }
}
