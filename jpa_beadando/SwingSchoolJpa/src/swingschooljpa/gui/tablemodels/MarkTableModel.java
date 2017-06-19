package swingschooljpa.gui.tablemodels;

import java.sql.Date;
import java.sql.SQLException;
import swingschooljpa.logic.DataSource;
import swingschooljpa.logic.entities.*;

public class MarkTableModel extends SchoolEntityTableModel<Mark> {

    public MarkTableModel() {
        super(Mark.fieldNames, DataSource.getInstance().getMarkController());
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return Student.class;
            case 1:
                return Subject.class;
            case 2:
                return Date.class;
            case 3:
                return Integer.class;
            default:
                return null;
        }
    }

    @Override
    protected Object getAttributeOfEntity(Mark mark, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return mark.getStudent();
            case 1:
                return mark.getSubject();
            case 2:
                return mark.getDatee();
            case 3:
                return mark.getMark();
            default:
                return null;
        }
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Mark mark, Object aValue) {
        switch (columnIndex) {
            case 0:
                mark.setStudent((Student) aValue);
                break;
            case 1:
                mark.setSubject((Subject) aValue);
                break;
            case 2:
                mark.setDatee(new Date(((java.util.Date) aValue).getTime()));
                break;
            case 3:
                mark.setMark((Integer) aValue);
                break;
        }
    }

    @Override
    public void addNewEntity() {
        try {
            if (DataSource.getInstance().getStudentController().getEntityCount() == 0) {
                throw new SQLException("There are no students yet");
            }
            if (DataSource.getInstance().getSubjectController().getEntityCount() == 0) {
                throw new SQLException("There are no subjects yet.");
            }
            Date date = new Date(new java.util.Date().getTime());
            Student student = DataSource.getInstance().getStudentController().getEntityByRowIndex(0);
            Subject subject = DataSource.getInstance().getSubjectController().getEntityByRowIndex(0);
            Mark mark = new Mark();
            mark.setDatee(date);
            mark.setMark(5);
            mark.setStudent(student);
            mark.setSubject(subject);
            addNewEntity(mark);
        } catch (SQLException ex) {
            displayError(ex);
        }
    }

}
