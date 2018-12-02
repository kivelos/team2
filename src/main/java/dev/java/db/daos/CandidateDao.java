package dev.java.db.daos;

import dev.java.db.model.Candidate;
import dev.java.db.model.ContactDetails;
import org.hibernate.Session;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CandidateDao extends AbstractDao<Candidate> {
    public CandidateDao(Session session) {
        super(session);
    }

    public List<Candidate> getCandidatesByPersonalDates(Candidate candidate) {
        String request = "FROM Candidate";
        if(( candidate.getName() != null && !candidate.getName().equals("") )
                || (candidate.getBirthday() != null)
                || ( candidate.getSurname() != null && !candidate.getSurname().equals("")))
            request += " WHERE ";
        boolean flagAnd = false;
        if (candidate.getName() != null && !candidate.getName().equals("")) {
            request += "name = \'"+candidate.getName()+"\'";
            flagAnd = true;
        }
        if (candidate.getSurname() != null && !candidate.getSurname().equals("")) {
            if (flagAnd)
                request += " AND ";
            request += "surname = \'"+candidate.getSurname()+"\'";
            flagAnd = true;
        }
        if (candidate.getBirthday() != null){
            if (flagAnd)
                request += " AND ";
            request += "birthday = \'"+candidate.getBirthday()+"\'";
        }
        Query query = getSession().createQuery(request);
        List<Candidate> candidates = ((org.hibernate.query.Query) query).list();
        return candidates;
    }

    public List<Candidate> getCandidatesByContact(ContactDetails contact) {
        String request = "FROM Candidate cand " +
                " WHERE contact in elements(cand.contactDetails)";
        Query query = getSession().createQuery(request);
        List<Candidate> candidates = ((org.hibernate.query.Query) query).list();
        return candidates;
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
