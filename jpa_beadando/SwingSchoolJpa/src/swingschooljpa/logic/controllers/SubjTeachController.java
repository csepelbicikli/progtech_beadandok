package swingschooljpa.logic.controllers;

import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import swingschooljpa.logic.DataSource;
import hu.elte.inf.prt.db.jpa.controllers.exceptions.NonexistentEntityException;
import swingschooljpa.logic.entities.Groupp;
import swingschooljpa.logic.entities.SubjTeach;
import swingschooljpa.logic.entities.Subject;
import swingschooljpa.logic.entities.Teacher;

public class SubjTeachController implements Serializable, EntityController<SubjTeach> {

    public SubjTeachController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SubjTeach subjTeach) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(subjTeach);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SubjTeach subjTeach) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            subjTeach = em.merge(subjTeach);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = subjTeach.getId();
                if (findSubjTeach(id) == null) {
                    throw new NonexistentEntityException("The subjTeach with id " + id + " no longer exists.");
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
            SubjTeach subjTeach;
            try {
                subjTeach = em.getReference(SubjTeach.class, id);
                subjTeach.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The subjTeach with id " + id + " no longer exists.", enfe);
            }
            em.remove(subjTeach);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SubjTeach> findSubjTeachEntities() {
        return findSubjTeachEntities(true, -1, -1);
    }

    public List<SubjTeach> findSubjTeachEntities(int maxResults, int firstResult) {
        return findSubjTeachEntities(false, maxResults, firstResult);
    }

    private List<SubjTeach> findSubjTeachEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SubjTeach.class));
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

    public SubjTeach findSubjTeach(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SubjTeach.class, id);
        } finally {
            em.close();
        }
    }

    public int getSubjTeachCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SubjTeach> rt = cq.from(SubjTeach.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public SubjTeach getEntityById(int entityId) {
        return findSubjTeach(entityId);
    }

    @Override
    public SubjTeach getEntityByRowIndex(int rowIndex) {
        return findSubjTeachEntities(1, rowIndex).get(0);
    }

    @Override
    public int getEntityCount() {
        return getSubjTeachCount();
    }

    @Override
    public void updateEntity(SubjTeach entity) throws Exception {
        edit(entity);
    }

//    @Override
//    public int addNewEntity() throws SQLException {
//        if (DataSource.getInstance().getSubjectController().getEntityCount() == 0) {
//            throw new SQLException("There are no subjects yet.");
//        }
//        if (DataSource.getInstance().getTeacherController().getEntityCount() == 0) {
//            throw new SQLException("There are no teachers yet.");
//        }
//        if (DataSource.getInstance().getGrouppController().getEntityCount() == 0) {
//            throw new SQLException("There are no groups yet.");
//        }
//        Subject subject = DataSource.getInstance().getSubjectController().getEntityByRowIndex(0);
//        Teacher teacher = DataSource.getInstance().getTeacherController().getEntityByRowIndex(0);
//        Groupp groupp = DataSource.getInstance().getGrouppController().getEntityByRowIndex(0);
//        SubjTeach subjTeach = new SubjTeach();
//        subjTeach.setGroupp(groupp);
//        subjTeach.setSubject(subject);
//        subjTeach.setTeacher(teacher);
//        create(subjTeach);
//        return getEntityCount() - 1;
//    }

    @Override
    public void deleteEntity(int rowIndex) throws NonexistentEntityException {
        destroy(getEntityByRowIndex(rowIndex).getId());
    }

    @Override
    public List<SubjTeach> getEntities() {
        return findSubjTeachEntities();
    }

    @Override
    public void addEntity(SubjTeach entity) {
        create(entity);
    }

}
