package swingschooljpa.gui.comboboxmodels;

import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import hu.elte.inf.prt.db.common.entities.EntityWithID;
import hu.elte.inf.prt.swing.comboboxmodels.JpaEntityComboBoxModel;
import java.sql.SQLException;
import swingschooljpa.gui.SwingSchoolFrame;

public class SchoolEntityComboBoxModel<E extends EntityWithID> extends JpaEntityComboBoxModel<E> {

    public SchoolEntityComboBoxModel(EntityController<E> controller) {
        super(controller);
    }

    @Override
    protected void displayError(SQLException sqlException) {
        SwingSchoolFrame.showError(sqlException.getMessage());
    }

}
