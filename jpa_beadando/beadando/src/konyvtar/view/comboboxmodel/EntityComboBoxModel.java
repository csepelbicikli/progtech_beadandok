

package konyvtar.view.comboboxmodel;

/**
 *
 * @author nemeth.peter
 */

import konyvtar.main.BeadandoMain;
import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import hu.elte.inf.prt.db.common.entities.EntityWithID;
import hu.elte.inf.prt.swing.comboboxmodels.JpaEntityComboBoxModel;
import java.sql.SQLException;

public class EntityComboBoxModel<E extends EntityWithID> extends JpaEntityComboBoxModel<E> {

    public EntityComboBoxModel(EntityController<E> controller) {
        super(controller);
    }

    @Override
    protected void displayError(SQLException sqlException) {
        BeadandoMain.showError(sqlException.getMessage());
    }

}