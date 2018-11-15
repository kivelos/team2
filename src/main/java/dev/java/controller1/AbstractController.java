package dev.java.controller1;

import dev.java.Logging;
import dev.java.db.daos1.AbstractDao;
import dev.java.db.model1.AbstractEntity;
import dev.java.db.utils.HibernateSessionFactory;
import org.hibernate.Session;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.List;

public abstract class AbstractController<T extends AbstractEntity> {
    private final Logging logging = new Logging();
    //private final boolean sortType = true;
    private String sortedField = "";
    private String url = "";
    private AbstractDao<T> abstractDao;
    private Session session;
    private int itemsInPage = GeneralConstant.ITEMS_IN_PAGE;

    @PostConstruct
    public void initialize() {
        try {
            session = HibernateSessionFactory.getSessionFactory().openSession();
        } catch (Exception e) {
            logging.runMe(e);
        }
    }

    @PreDestroy
    public void destroy() {
        try {
            HibernateSessionFactory.shutdown();
        } catch (Exception e) {
            logging.runMe(e);
        }
    }

    public ResponseEntity getAllEntities(HttpServletRequest request) {
        logging.runMe(request);
        List<T> allEntities;
        try {
            allEntities = abstractDao.getSortedEntitiesPage(1, sortedField, true, itemsInPage);
            return ResponseEntity.ok(allEntities);
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    public ResponseEntity createEntity(@RequestBody T entity, HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (abstractDao.createEntity(entity)) {
                return ResponseEntity.created(new URI(url + entity.getId())).build();
            }
            return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).contentType(MediaType.TEXT_PLAIN)
                    .body("Invalid Input");
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    public ResponseEntity getEntity(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        try {
            T entity = abstractDao.getEntityById(id);
            if (entity == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(entity);
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    public ResponseEntity updateEntity(@PathVariable long id, @RequestBody T entity,
                                       HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (abstractDao.updateEntity(entity)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    public ResponseEntity deleteEntity(@PathVariable long id, HttpServletRequest request) {
        logging.runMe(request);
        try {
            if (abstractDao.deleteEntityById(id)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return getResponseEntityOnServerError(e);
        }
    }

    private ResponseEntity getResponseEntityOnServerError(Exception e) {
        logging.runMe(e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).contentType(MediaType.TEXT_PLAIN)
                .body("Server error");
    }

    public String getSortedField() {
        return sortedField;
    }

    public void setSortedField(String sortedField) {
        this.sortedField = sortedField;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AbstractDao<T> getAbstractDao() {
        return abstractDao;
    }

    public void setAbstractDao(AbstractDao<T> abstractDao) {
        this.abstractDao = abstractDao;
    }

    public int getItemsInPage() {
        return itemsInPage;
    }

    public void setItemsInPage(int itemsInPage) {
        this.itemsInPage = itemsInPage;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
