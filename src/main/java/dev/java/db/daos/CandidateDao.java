package dev.java.db.daos1;

import dev.java.db.model1.Candidate;
import org.hibernate.Session;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CandidateDao extends AbstractDao<Candidate> {
    public CandidateDao(Session session) {
        super(session);
    }

    @Override
    public List<Candidate> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                 boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSession().getCriteriaBuilder();
        CriteriaQuery<Candidate> query = criteriaBuilder.createQuery(Candidate.class);
        Root<Candidate> root = query.from(Candidate.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<Candidate> typedQuery = getSession().createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemsNumberInPage);
        typedQuery.setMaxResults(itemsNumberInPage);

        return typedQuery.getResultList();
    }

    @Override
    public List<Candidate> getFilteredEntitiesPage(String... params) {
        return null;
    }

    @Override
    public Candidate getEntityById(long id) {
        return getSession().get(Candidate.class, id);
    }
}
