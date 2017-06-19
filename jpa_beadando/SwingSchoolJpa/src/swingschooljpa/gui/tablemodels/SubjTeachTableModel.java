package swingschooljpa.gui.tablemodels;

import java.sql.SQLException;
import swingschooljpa.logic.DataSource;
import swingschooljpa.logic.entities.*;

public class SubjTeachTableModel extends SchoolEntityTableModel<SubjTeach> {

    public SubjTeachTableModel() {
        super(SubjTeach.fieldNames, DataSource.getInstance().getSubjTeachController());
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Teacher.class;
            case 1:
                return Subject.class;
            case 2:
                return Groupp.class;
            default:
                return null;
        }
    }

    @Override
    protected Object getAttributeOfEntity(SubjTeach subjTeach, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return subjTeach.getTeacher();
            case 1:
                return subjTeach.getSubject();
            case 2:
                return subjTeach.getGroupp();
            default:
                return null;
        }
    }

    @Override
    protected void setEntityAttributes(int columnIndex, SubjTeach subjTeach, Object aValue) {
        switch (columnIndex) {
            case 0:
                subjTeach.setTeacher((Teacher) aValue);
                break;
            case 1:
                subjTeach.setSubject((Subject) aValue);
                break;
            case 2:
                subjTeach.setGroupp((Groupp) aValue);
                break;
        }
    }

    @Override
    public void addNewEntity() {
        try {
            if (DataSource.getInstance().getSubjectController().getEntityCount() == 0) {
                throw new SQLException("There are no subjects yet.");
            }
            if (DataSource.getInstance().getTeacherController().getEntityCount() == 0) {
                throw new SQLException("There are no teachers yet.");
            }
            if (DataSource.getInstance().getGrouppController().getEntityCount() == 0) {
                throw new SQLException("There are no groups yet.");
            }
            Subject subject = DataSource.getInstance().getSubjectController().getEntityByRowIndex(0);
            Teacher teacher = DataSource.getInstance().getTeacherController().getEntityByRowIndex(0);
            Groupp groupp = DataSource.getInstance().getGrouppController().getEntityByRowIndex(0);
            SubjTeach subjTeach = new SubjTeach();
            subjTeach.setGroupp(groupp);
            subjTeach.setSubject(subject);
            subjTeach.setTeacher(teacher);
            addNewEntity(subjTeach);
        } catch (SQLException ex) {
            displayError(ex);
        }
    }

}
