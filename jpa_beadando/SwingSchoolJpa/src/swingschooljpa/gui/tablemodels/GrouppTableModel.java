package swingschooljpa.gui.tablemodels;

import swingschooljpa.logic.DataSource;
import swingschooljpa.logic.entities.Groupp;

public class GrouppTableModel extends SchoolEntityTableModel<Groupp>  {

    public GrouppTableModel() {
        super(Groupp.fieldNames, DataSource.getInstance().getGrouppController());
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
    protected Object getAttributeOfEntity(Groupp groupp, int columnIndex) {
        return groupp.getName();
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Groupp groupp, Object aValue) {
        groupp.setName((String) aValue);
    }

    @Override
    public void addNewEntity() {
        Groupp groupp = new Groupp();
        groupp.setName("<new group>");
        addNewEntity(groupp);
    }

}
