package swingschooljpa.gui.tablemodels;

import swingschooljpa.logic.DataSource;
import swingschooljpa.logic.entities.*;

public class SubjectTableModel extends SchoolEntityTableModel<Subject> {

    public SubjectTableModel() {
        super(Subject.fieldNames, DataSource.getInstance().getSubjectController());
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
    protected Object getAttributeOfEntity(Subject subject, int columnIndex) {
        return subject.getTitle();
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Subject subject, Object aValue) {
        subject.setTitle((String) aValue);
    }

    @Override
    public void addNewEntity() {
        String title = "<new subject>";
        Subject subject = new Subject();
        subject.setTitle(title);
        addNewEntity(subject);
    }
}
