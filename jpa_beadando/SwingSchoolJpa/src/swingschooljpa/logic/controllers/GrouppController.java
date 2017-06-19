package swingschooljpa.logic.controllers;

import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import swingschooljpa.logic.entities.Student;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import hu.elte.inf.prt.db.jpa.controllers.exceptions.NonexistentEntityException;
import swingschooljpa.logic.entities.Groupp;

public class GrouppController implements Serializable, EntityController<Groupp> {

    public GrouppController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Groupp groupp) {
        if (groupp.getStudents() == null) {
            groupp.setStudents(new ArrayList<Student>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Student> attachedStudents = new ArrayList<Student>();
            for (Student studentsStudentToAttach : groupp.getStudents()) {
                studentsStudentToAttach = em.getReference(studentsStudentToAttach.getClass(), studentsStudentToAttach.getId());
                attachedStudents.add(studentsStudentToAttach);
            }
            groupp.setStudents(attachedStudents);
            em.persist(groupp);
            for (Student studentsStudent : groupp.getStudents()) {
                Groupp oldGrouppOfStudentsStudent = studentsStudent.getGroupp();
                studentsStudent.setGroupp(groupp);
                studentsStudent = em.merge(studentsStudent);
                if (oldGrouppOfStudentsStudent != null) {
                    oldGrouppOfStudentsStudent.getStudents().remove(studentsStudent);
                    oldGrouppOfStudentsStudent = em.merge(oldGrouppOfStudentsStudent);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Groupp groupp) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupp persistentGroupp = em.find(Groupp.class, groupp.getId());
            List<Student> studentsOld = persistentGroupp.getStudents();
            List<Student> studentsNew = groupp.getStudents();
            List<Student> attachedStudentsNew = new ArrayList<Student>();
            for (Student studentsNewStudentToAttach : studentsNew) {
                studentsNewStudentToAttach = em.getReference(studentsNewStudentToAttach.getClass(), studentsNewStudentToAttach.getId());
                attachedStudentsNew.add(studentsNewStudentToAttach);
            }
            studentsNew = attachedStudentsNew;
            groupp.setStudents(studentsNew);
            groupp = em.merge(groupp);
            for (Student studentsOldStudent : studentsOld) {
                if (!studentsNew.contains(studentsOldStudent)) {
                    studentsOldStudent.setGroupp(null);
                    studentsOldStudent = em.merge(studentsOldStudent);
                }
            }
            for (Student studentsNewStudent : studentsNew) {
                if (!studentsOld.contains(studentsNewStudent)) {
                    Groupp oldGrouppOfStudentsNewStudent = studentsNewStudent.getGroupp();
                    studentsNewStudent.setGroupp(groupp);
                    studentsNewStudent = em.merge(studentsNewStudent);
                    if (oldGrouppOfStudentsNewStudent != null && !oldGrouppOfStudentsNewStudent.equals(groupp)) {
                        oldGrouppOfStudentsNewStudent.getStudents().remove(studentsNewStudent);
                        oldGrouppOfStudentsNewStudent = em.merge(oldGrouppOfStudentsNewStudent);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = groupp.getId();
                if (findGroupp(id) == null) {
                    throw new NonexistentEntityException("The groupp with id " + id + " no longer exists.");
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
            Groupp groupp;
            try {
                groupp = em.getReference(Groupp.class, id);
                groupp.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The groupp with id " + id + " no longer exists.", enfe);
            }
            List<Student> students = groupp.getStudents();
            for (Student studentsStudent : students) {
                studentsStudent.setGroupp(null);
                studentsStudent = em.merge(studentsStudent);
            }
            em.remove(groupp);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Groupp> findGrouppEntities() {
        return findGrouppEntities(true, -1, -1);
    }

    public List<Groupp> findGrouppEntities(int maxResults, int firstResult) {
        return findGrouppEntities(false, maxResults, firstResult);
    }

    private List<Groupp> findGrouppEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Groupp.class));
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

    public Groupp findGroupp(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Groupp.class, id);
        } finally {
            em.close();
        }
    }

    public int getGrouppCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Groupp> rt = cq.from(Groupp.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Groupp getEntityById(int entityId) {
        return findGroupp(entityId);
    }

    @Override
    public Groupp getEntityByRowIndex(int rowIndex) {
        return findGrouppEntities(1, rowIndex).get(0);
    }

    @Override
    public int getEntityCount() {
        return getGrouppCount();
    }

    @Override
    public void updateEntity(Groupp entity) throws Exception {
        edit(entity);
    }

//    @Override
//    public int addNewEntity() {
//        String name = "[new group]";
//        Groupp groupp = new Groupp();
//        groupp.setName(name);
//        create(groupp);
//        return getEntityCount() - 1;
//    }

    @Override
    public void deleteEntity(int rowIndex) throws NonexistentEntityException {
        destroy(getEntityByRowIndex(rowIndex).getId());
    }

    @Override
    public List<Groupp> getEntities() {
        return findGrouppEntities();
    }

    @Override
    public void addEntity(Groupp entity) {
        create(entity);
    }

}
