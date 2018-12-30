package dev.java.db.daos;

import dev.java.db.model.Vacancy;
import org.hibernate.SessionFactory;

import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class VacancyDao extends AbstractDao<Vacancy> {
    public VacancyDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Vacancy> getSortedEntitiesPage(int pageNumber, String sortedField,
                                            boolean order, int itemsNumberInPage) {
        CriteriaBuilder criteriaBuilder = getSessionFactory().getCriteriaBuilder();
        CriteriaQuery<Vacancy> query = criteriaBuilder.createQuery(Vacancy.class);
        Root<Vacancy> root = query.from(Vacancy.class);

        if (order) {
            query = query.select(root).orderBy(criteriaBuilder.asc(root.get(sortedField)));
        } else {
            query = query.select(root).orderBy(criteriaBuilder.desc(root.get(sortedField)));
        }

        TypedQuery<Vacancy> typedQuery = getSessionFactory().getCurrentSession().createQuery(query);
        typedQuery.setFirstResult((pageNumber - 1) * itemsNumberInPage);
        typedQuery.setMaxResults(itemsNumberInPage);

        return typedQuery.getResultList();
    }

    @Override
    public List<Vacancy> getFilteredEntitiesPage(String... params) {
        return null;
    }

    @Override
    public Vacancy getEntityById(long id) {
        return getSessionFactory().getCurrentSession().get(Vacancy.class, id);
    }
}
