package swingschooljpa.logic.controllers;

import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hu.elte.inf.prt.db.jpa.controllers.exceptions.NonexistentEntityException;
import swingschooljpa.logic.entities.Subject;

public class SubjectController implements Serializable, EntityController<Subject> {

    public SubjectController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Subject subject) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(subject);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Subject subject) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            subject = em.merge(subject);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subject.getId();
                if (findSubject(id) == null) {
                    throw new NonexistentEntityException("The subject with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Subject subject;
            try {
                subject = em.getReference(Subject.class, id);
                subject.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subject with id " + id + " no longer exists.", enfe);
            }
            em.remove(subject);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Subject> findSubjectEntities() {
        return findSubjectEntities(true, -1, -1);
    }

    public List<Subject> findSubjectEntities(int maxResults, int firstResult) {
        return findSubjectEntities(false, maxResults, firstResult);
    }

    private List<Subject> findSubjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Subject.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Subject findSubject(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Subject.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubjectCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Subject> rt = cq.from(Subject.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Subject getEntityById(int entityId) {
        return findSubject(entityId);
    }

    @Override
    public Subject getEntityByRowIndex(int rowIndex) {
        return findSubjectEntities(1, rowIndex).get(0);
    }

    @Override
    public int getEntityCount() {
        return getSubjectCount();
    }

    @Override
    public void updateEntity(Subject entity) throws Exception {
        edit(entity);
    }

//    @Override
//    public int addNewEntity() {
//        String title = "<new subject>";
//        Subject subject = new Subject();
//        subject.setTitle(title);
//        create(subject);
//        return getEntityCount() - 1;
//    }

    @Override
    public void deleteEntity(int rowIndex) throws NonexistentEntityException {
        destroy(getEntityByRowIndex(rowIndex).getId());
    }

    @Override
    public List<Subject> getEntities() {
        return findSubjectEntities();
    }

    @Override
    public void addEntity(Subject entity) {
        create(entity);
    }

}
