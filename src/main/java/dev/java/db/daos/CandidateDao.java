package dev.java.db.daos;

import dev.java.db.model.Candidate;
import dev.java.db.model.ContactDetails;
import org.hibernate.Query;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class CandidateDao extends AbstractDao<Candidate> {
    public CandidateDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Candidate> getCandidatesByPersonalData(Candidate candidate) {
        String request = "FROM Candidate";
        if ((candidate.getName() != null && !candidate.getName().equals(""))
                || (candidate.getBirthday() != null)
                || (candidate.getSurname() != null && !candidate.getSurname().equals(""))) {
            request += " WHERE ";
        }
        boolean flagAnd = false;
        if (candidate.getName() != null && !candidate.getName().equals("")) {
            request += "name = \'" + candidate.getName() + "\'";
            flagAnd = true;
        }
        if (candidate.getSurname() != null && !candidate.getSurname().equals("")) {
            if (flagAnd) {
                request += " AND ";
            }
            request += "surname = \'" + candidate.getSurname() + "\'";
            flagAnd = true;
        }
        if (candidate.getBirthday() != null) {
            if (flagAnd) {
                request += " AND ";
            }
            request += "birthday = \'" + candidate.getBirthday() + "\'";
        }
        Query query = getSessionFactory().getCurrentSession().createQuery(request);
        List<Candidate> candidates = ((org.hibernate.query.Query) query).list();
        return candidates;
    }

    public List<Candidate> getCandidatesByContact(ContactDetails contact) {
        String request = "FROM Candidate cand JOIN cand.contactDetails cd WHERE cd.contactDetails = :contact";
        Query<Candidate> query = getSessionFactory().getCurrentSession().createQuery(request, Candidate.class);
        query.setParameter("contact", contact);

        return query.list();
    }

    @Override
    public List<Candidate> getSortedEntitiesPage(int pageNumber, String sortedField,
                                                 boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSessionFactory().getCriteriaBuilder();
        CriteriaQuery<Candidate> query = criteriaBuilder.createQuery(Candidate.class);
        Root<Candidate> root = query.from(Candidate.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<Candidate> typedQuery = getSessionFactory().getCurrentSession().createQuery(query);
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
        return getSessionFactory().getCurrentSession().get(Candidate.class, id);
    }
}

//select  candidate0_.id as id1_0_,
//        candidate0_.birthday as birthday2_0_,
//        candidate0_.candidate_state as candidat6_0_,
//        candidate0_.name as name3_0_,
//        candidate0_.salary_in_dollars as salary_i4_0_,
//        candidate0_.surname as surname5_0_
//from candidate candidate0_
//            cross join contact_details contactdet1_ where candidate0_.id=contactdet1_.id_candidate and .=?
