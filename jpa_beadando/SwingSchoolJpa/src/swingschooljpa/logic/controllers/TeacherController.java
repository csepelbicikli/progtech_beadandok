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
import swingschooljpa.logic.entities.Teacher;

public class TeacherController implements Serializable, EntityController<Teacher> {

    public TeacherController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Teacher teacher) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(teacher);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Teacher teacher) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            teacher = em.merge(teacher);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = teacher.getId();
                if (findTeacher(id) == null) {
                    throw new NonexistentEntityException("The teacher with id " + id + " no longer exists.");
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
            Teacher teacher;
            try {
                teacher = em.getReference(Teacher.class, id);
                teacher.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The teacher with id " + id + " no longer exists.", enfe);
            }
            em.remove(teacher);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Teacher> findTeacherEntities() {
        return findTeacherEntities(true, -1, -1);
    }

    public List<Teacher> findTeacherEntities(int maxResults, int firstResult) {
        return findTeacherEntities(false, maxResults, firstResult);
    }

    private List<Teacher> findTeacherEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Teacher.class));
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

    public Teacher findTeacher(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Teacher.class, id);
        } finally {
            em.close();
        }
    }

    public int getTeacherCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Teacher> rt = cq.from(Teacher.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Teacher getEntityById(int entityId) {
        return findTeacher(entityId);
    }

    @Override
    public Teacher getEntityByRowIndex(int rowIndex) {
        return findTeacherEntities(1, rowIndex).get(0);
    }

    @Override
    public int getEntityCount() {
        return getTeacherCount();
    }

    @Override
    public void updateEntity(Teacher entity) throws Exception {
        edit(entity);
    }

//    @Override
//    public int addNewEntity() {
//        String firstName = "<first name>", lastName = "<last name>";
//        Teacher teacher = new Teacher();
//        teacher.setFirstName(firstName);
//        teacher.setLastName(lastName);
//        create(teacher);
//        return getEntityCount() - 1;
//    }

    @Override
    public void deleteEntity(int rowIndex) throws NonexistentEntityException {
        destroy(getEntityByRowIndex(rowIndex).getId());
    }

    @Override
    public List<Teacher> getEntities() {
        return findTeacherEntities();
    }

    @Override
    public void addEntity(Teacher entity) {
        create(entity);
    }
    
}
