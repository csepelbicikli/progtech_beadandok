package swingschooljpa.logic;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import swingschooljpa.logic.controllers.GrouppController;
import swingschooljpa.logic.controllers.MarkController;
import swingschooljpa.logic.controllers.StudentController;
import swingschooljpa.logic.controllers.SubjTeachController;
import swingschooljpa.logic.controllers.SubjectController;
import swingschooljpa.logic.controllers.TeacherController;

public class DataSource {

    private final GrouppController grouppController;
    private final StudentController studentController;
    private final TeacherController teacherController;
    private final SubjectController subjectController;
    private final SubjTeachController subjTeachController;
    private final MarkController markController;

    private DataSource() {
        grouppController = new GrouppController(getEntityManagerFactory());
        studentController = new StudentController(getEntityManagerFactory());
        teacherController = new TeacherController(getEntityManagerFactory());
        subjectController = new SubjectController(getEntityManagerFactory());
        subjTeachController = new SubjTeachController(getEntityManagerFactory());
        markController = new MarkController(getEntityManagerFactory());
    }

    public final EntityManagerFactory getEntityManagerFactory() {
        return Persistence.createEntityManagerFactory("SwingSchoolJpaPU");
    }

    public GrouppController getGrouppController() {
        return grouppController;
    }

    public StudentController getStudentController() {
        return studentController;
    }

    public TeacherController getTeacherController() {
        return teacherController;
    }

    public SubjectController getSubjectController() {
        return subjectController;
    }

    public SubjTeachController getSubjTeachController() {
        return subjTeachController;
    }

    public MarkController getMarkController() {
        return markController;
    }

    public static DataSource getInstance() {
        return DataSourceHolder.INSTANCE;
    }

    private static class DataSourceHolder {

        private static final DataSource INSTANCE = new DataSource();
    }
}
