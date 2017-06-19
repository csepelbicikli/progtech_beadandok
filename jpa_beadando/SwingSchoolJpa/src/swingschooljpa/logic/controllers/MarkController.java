package swingschooljpa.logic.controllers;

import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import java.io.Serializable;
import java.sql.Date;
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
import swingschooljpa.logic.entities.Mark;
import swingschooljpa.logic.entities.Student;
import swingschooljpa.logic.entities.Subject;

public class MarkController implements Serializable, EntityController<Mark> {

    public MarkController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Mark mark) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student student = mark.getStudent();
            if (student != null) {
                student = em.getReference(student.getClass(), student.getId());
                mark.setStudent(student);
            }
            em.persist(mark);
            if (student != null) {
                student.getMarks().add(mark);
                student = em.merge(student);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Mark mark) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Mark persistentMark = em.find(Mark.class, mark.getId());
            Student studentOld = persistentMark.getStudent();
            Student studentNew = mark.getStudent();
            if (studentNew != null) {
                studentNew = em.getReference(studentNew.getClass(), studentNew.getId());
                mark.setStudent(studentNew);
            }
            mark = em.merge(mark);
            if (studentOld != null && !studentOld.equals(studentNew)) {
                studentOld.getMarks().remove(mark);
                studentOld = em.merge(studentOld);
            }
            if (studentNew != null && !studentNew.equals(studentOld)) {
                studentNew.getMarks().add(mark);
                studentNew = em.merge(studentNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = mark.getId();
                if (findMark(id) == null) {
                    throw new NonexistentEntityException("The mark with id " + id + " no longer exists.");
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
            Mark mark;
            try {
                mark = em.getReference(Mark.class, id);
                mark.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The mark with id " + id + " no longer exists.", enfe);
            }
            Student student = mark.getStudent();
            if (student != null) {
                student.getMarks().remove(mark);
                student = em.merge(student);
            }
            em.remove(mark);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Mark> findMarkEntities() {
        return findMarkEntities(true, -1, -1);
    }

    public List<Mark> findMarkEntities(int maxResults, int firstResult) {
        return findMarkEntities(false, maxResults, firstResult);
    }

    private List<Mark> findMarkEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Mark.class));
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

    public Mark findMark(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Mark.class, id);
        } finally {
            em.close();
        }
    }

    public int getMarkCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Mark> rt = cq.from(Mark.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Mark getEntityById(int entityId) {
        return findMark(entityId);
    }

    @Override
    public Mark getEntityByRowIndex(int rowIndex) {
        return findMarkEntities(1, rowIndex).get(0);
    }

    @Override
    public int getEntityCount() {
        return getMarkCount();
    }

    @Override
    public void updateEntity(Mark entity) throws Exception {
        edit(entity);
    }

//    @Override
//    public int addNewEntity() throws SQLException {
//        if (DataSource.getInstance().getStudentController().getEntityCount() == 0) {
//            throw new SQLException("There are no students yet");
//        }
//        if (DataSource.getInstance().getSubjectController().getEntityCount() == 0) {
//            throw new SQLException("There are no subjects yet.");
//        }
//        Date date = new Date(new java.util.Date().getTime());
//        int mark = 5;
//        Student student = DataSource.getInstance().getStudentController().getEntityByRowIndex(0);
//        Subject subject = DataSource.getInstance().getSubjectController().getEntityByRowIndex(0);
//        Mark markk = new Mark();
//        markk.setDatee(date);
//        markk.setMark(mark);
//        markk.setStudent(student);
//        markk.setSubject(subject);
//        create(markk);
//        return getEntityCount() - 1;
//    }

    @Override
    public void deleteEntity(int rowIndex) throws NonexistentEntityException {
        destroy(getEntityByRowIndex(rowIndex).getId());
    }

    @Override
    public List<Mark> getEntities() {
        return findMarkEntities();
    }

    @Override
    public void addEntity(Mark entity) {
        create(entity);
    }

}
