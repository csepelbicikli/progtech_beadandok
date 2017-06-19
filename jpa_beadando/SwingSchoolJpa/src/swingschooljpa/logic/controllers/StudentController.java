package swingschooljpa.logic.controllers;

import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import java.io.Serializable;
import java.sql.SQLException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import swingschooljpa.logic.entities.Groupp;
import swingschooljpa.logic.entities.Mark;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import swingschooljpa.logic.DataSource;
import hu.elte.inf.prt.db.jpa.controllers.exceptions.NonexistentEntityException;
import swingschooljpa.logic.entities.Student;

public class StudentController implements Serializable, EntityController<Student> {

    public StudentController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Student student) {
        if (student.getMarks() == null) {
            student.setMarks(new ArrayList<Mark>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Groupp groupp = student.getGroupp();
            if (groupp != null) {
                groupp = em.getReference(groupp.getClass(), groupp.getId());
                student.setGroupp(groupp);
            }
            List<Mark> attachedMarks = new ArrayList<Mark>();
            for (Mark marksMarkToAttach : student.getMarks()) {
                marksMarkToAttach = em.getReference(marksMarkToAttach.getClass(), marksMarkToAttach.getId());
                attachedMarks.add(marksMarkToAttach);
            }
            student.setMarks(attachedMarks);
            em.persist(student);
            if (groupp != null) {
                groupp.getStudents().add(student);
                groupp = em.merge(groupp);
            }
            for (Mark marksMark : student.getMarks()) {
                Student oldStudentOfMarksMark = marksMark.getStudent();
                marksMark.setStudent(student);
                marksMark = em.merge(marksMark);
                if (oldStudentOfMarksMark != null) {
                    oldStudentOfMarksMark.getMarks().remove(marksMark);
                    oldStudentOfMarksMark = em.merge(oldStudentOfMarksMark);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Student student) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Student persistentStudent = em.find(Student.class, student.getId());
            Groupp grouppOld = persistentStudent.getGroupp();
            Groupp grouppNew = student.getGroupp();
            List<Mark> marksOld = persistentStudent.getMarks();
            List<Mark> marksNew = student.getMarks();
            if (grouppNew != null) {
                grouppNew = em.getReference(grouppNew.getClass(), grouppNew.getId());
                student.setGroupp(grouppNew);
            }
            List<Mark> attachedMarksNew = new ArrayList<Mark>();
            for (Mark marksNewMarkToAttach : marksNew) {
                marksNewMarkToAttach = em.getReference(marksNewMarkToAttach.getClass(), marksNewMarkToAttach.getId());
                attachedMarksNew.add(marksNewMarkToAttach);
            }
            marksNew = attachedMarksNew;
            student.setMarks(marksNew);
            student = em.merge(student);
            if (grouppOld != null && !grouppOld.equals(grouppNew)) {
                grouppOld.getStudents().remove(student);
                grouppOld = em.merge(grouppOld);
            }
            if (grouppNew != null && !grouppNew.equals(grouppOld)) {
                grouppNew.getStudents().add(student);
                grouppNew = em.merge(grouppNew);
            }
            for (Mark marksOldMark : marksOld) {
                if (!marksNew.contains(marksOldMark)) {
                    marksOldMark.setStudent(null);
                    marksOldMark = em.merge(marksOldMark);
                }
            }
            for (Mark marksNewMark : marksNew) {
                if (!marksOld.contains(marksNewMark)) {
                    Student oldStudentOfMarksNewMark = marksNewMark.getStudent();
                    marksNewMark.setStudent(student);
                    marksNewMark = em.merge(marksNewMark);
                    if (oldStudentOfMarksNewMark != null && !oldStudentOfMarksNewMark.equals(student)) {
                        oldStudentOfMarksNewMark.getMarks().remove(marksNewMark);
                        oldStudentOfMarksNewMark = em.merge(oldStudentOfMarksNewMark);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = student.getId();
                if (findStudent(id) == null) {
                    throw new NonexistentEntityException("The student with id " + id + " no longer exists.");
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
            Student student;
            try {
                student = em.getReference(Student.class, id);
                student.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The student with id " + id + " no longer exists.", enfe);
            }
            Groupp groupp = student.getGroupp();
            if (groupp != null) {
                groupp.getStudents().remove(student);
                groupp = em.merge(groupp);
            }
            List<Mark> marks = student.getMarks();
            for (Mark marksMark : marks) {
                marksMark.setStudent(null);
                marksMark = em.merge(marksMark);
            }
            em.remove(student);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Student> findStudentEntities() {
        return findStudentEntities(true, -1, -1);
    }

    public List<Student> findStudentEntities(int maxResults, int firstResult) {
        return findStudentEntities(false, maxResults, firstResult);
    }

    private List<Student> findStudentEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Student.class));
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

    public Student findStudent(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Student.class, id);
        } finally {
            em.close();
        }
    }

    public int getStudentCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Student> rt = cq.from(Student.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    @Override
    public Student getEntityById(int entityId) {
        return findStudent(entityId);
    }

    @Override
    public Student getEntityByRowIndex(int rowIndex) {
        return findStudentEntities(1, rowIndex).get(0);
    }

    @Override
    public int getEntityCount() {
        return getStudentCount();
    }

    @Override
    public void updateEntity(Student entity) throws Exception {
        edit(entity);
    }

//    @Override
//    public int addNewEntity() throws SQLException {
//        if (DataSource.getInstance().getGrouppController().getEntityCount() == 0) {
//            throw new SQLException("There are no groups yet.");
//        }
//        String firstName = "<first name>", lastName = "<last name>";
//        Groupp groupp = DataSource.getInstance().getGrouppController().getEntityByRowIndex(0);
//        Student student = new Student();
//        student.setFirstName(firstName);
//        student.setLastName(lastName);
//        student.setGroupp(groupp);
//        create(student);
//        return getEntityCount() - 1;
//    }

    @Override
    public void deleteEntity(int rowIndex) throws NonexistentEntityException {
        destroy(getEntityByRowIndex(rowIndex).getId());
    }

    @Override
    public List<Student> getEntities() {
        return findStudentEntities();
    }

    @Override
    public void addEntity(Student entity) {
        create(entity);
    }

}
