package swingschooljpa.gui.tablemodels;

import java.sql.SQLException;
import swingschooljpa.logic.DataSource;
import swingschooljpa.logic.entities.*;

public class StudentTableModel extends SchoolEntityTableModel<Student>{

    public StudentTableModel() {
        super(Student.fieldNames, DataSource.getInstance().getStudentController());
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnIndex == 2 ? Groupp.class : String.class;
    }

    @Override
    protected Object getAttributeOfEntity(Student student, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return student.getFirstName();
            case 1:
                return student.getLastName();
            case 2:
                return student.getGroupp().getName();
            default:
                return null;
        }
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Student student, Object aValue) {
        switch (columnIndex) {
            case 0:
                student.setFirstName((String) aValue);
                break;
            case 1:
                student.setLastName((String) aValue);
                break;
            case 2:
                student.setGroupp((Groupp) aValue);
                break;
        }
    }

    @Override
    public void addNewEntity() {
        try {
            if (DataSource.getInstance().getGrouppController().getEntityCount() == 0) {
                throw new SQLException("There are no groups yet.");
            }
            String firstName = "<first name>", lastName = "<last name>";
            Groupp groupp = DataSource.getInstance().getGrouppController().getEntityByRowIndex(0);
            Student student = new Student();
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setGroupp(groupp);
            addNewEntity(student);
        } catch (SQLException ex) {
            displayError(ex);
        }
    }

}
