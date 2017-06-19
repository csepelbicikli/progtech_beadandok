package swingschooljpa.gui.tablemodels;

import swingschooljpa.logic.DataSource;
import swingschooljpa.logic.entities.Teacher;

public class TeacherTableModel extends SchoolEntityTableModel<Teacher> {

    public TeacherTableModel() {
        super(Teacher.fieldNames, DataSource.getInstance().getTeacherController());
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    @Override
    protected Object getAttributeOfEntity(Teacher teacher, int columnIndex) {
        return columnIndex == 0 ? teacher.getFirstName() : teacher.getLastName();
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Teacher teacher, Object aValue) {
        if (columnIndex == 0) {
            teacher.setFirstName((String) aValue);
        } else {
            teacher.setLastName((String) aValue);
        }
    }

    @Override
    public void addNewEntity() {
        String firstName = "<first name>", lastName = "<last name>";
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        addNewEntity(teacher);
    }

}
