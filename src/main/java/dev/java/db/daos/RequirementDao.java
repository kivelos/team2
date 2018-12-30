package dev.java.db.daos;

import dev.java.db.model.Requirement;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
public class RequirementDao {
    private SessionFactory sessionFactory;

    public RequirementDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Requirement> getAllRequirements() {
        CriteriaBuilder cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Requirement> cq = cb.createQuery(Requirement.class);
        Root<Requirement> rootEntry = cq.from(Requirement.class);
        CriteriaQuery<Requirement> all = cq.select(rootEntry);

        TypedQuery<Requirement> allQuery = sessionFactory.getCurrentSession().createQuery(all);
        return allQuery.getResultList();
    }
}
