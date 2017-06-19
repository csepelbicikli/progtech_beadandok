package swingschooljpa.gui.tablemodels;

import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import hu.elte.inf.prt.db.common.entities.EntityWithID;
import hu.elte.inf.prt.swing.tablemodels.AbstractJpaEntityTableModel;
import java.sql.SQLException;
import swingschooljpa.gui.SwingSchoolFrame;

public abstract class SchoolEntityTableModel<E extends EntityWithID> extends AbstractJpaEntityTableModel<E> {

    public SchoolEntityTableModel(String[] columnNames, EntityController<E> controller) {
        super(columnNames, controller);
    }

    @Override
    protected void displayError(SQLException sqlException) {
        SwingSchoolFrame.showError(sqlException.getMessage());
    }
    
    public E getEntityByID(Number id){
        for (E entity: entities){
            if (id==entity.getId()){
                return entity;
            }
        }
        return null;
    }
   
}
