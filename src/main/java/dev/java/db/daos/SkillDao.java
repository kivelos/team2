package dev.java.db.daos;

import dev.java.db.model.Skill;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

@Transactional
public class SkillDao {
    private SessionFactory sessionFactory;

    public SkillDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<Skill> getAllSkills() {
        CriteriaBuilder cb = sessionFactory.getCurrentSession().getCriteriaBuilder();
        CriteriaQuery<Skill> cq = cb.createQuery(Skill.class);
        Root<Skill> rootEntry = cq.from(Skill.class);
        CriteriaQuery<Skill> all = cq.select(rootEntry);

        TypedQuery<Skill> allQuery = sessionFactory.getCurrentSession().createQuery(all);
        return allQuery.getResultList();
    }
}
