

package konyvtar.view.tablemodel;

import konyvtar.main.BeadandoMain;
import hu.elte.inf.prt.db.common.entities.EntityWithID;
import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import hu.elte.inf.prt.swing.tablemodels.AbstractJpaEntityTableModel;
import java.sql.SQLException;
import javax.swing.SwingWorker;

/**
 *
 * @author nemeth.peter
 */
public abstract class EntityTableModel<E extends EntityWithID> extends AbstractJpaEntityTableModel<E> {

    public EntityTableModel(String[] columnNames, EntityController<E> controller) {
        super(columnNames, controller);
    }

    @Override
    protected void displayError(SQLException sqlException) {
        BeadandoMain.showError(sqlException.getMessage());
    }
    
    @Override
    public void addNewEntity(final E entity){
        super.addNewEntity(entity);
    }
   
    
     @Override
    public void addNewEntity() {
        //mas szerkesztesi modszert alkalmazok
    }

}
