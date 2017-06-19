package hu.elte.inf.prt.swing.comboboxmodels;

import hu.elte.inf.prt.db.jdbc.controllers.EntityController;
import hu.elte.inf.prt.db.common.entities.EntityWithID;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SwingWorker;
import javax.swing.Timer;

public abstract class JdbcEntityComboBoxModel<E extends EntityWithID> extends DefaultComboBoxModel<E> {

    private List<E> entities;
    private final int refreshInterval;
    private final Timer refreshTimer;
    private final EntityController<E> controller;

    public JdbcEntityComboBoxModel(EntityController<E> controller) {
        this.controller = controller;
        entities = new ArrayList<>();
        this.refreshInterval = 60 * 1000;
        this.refreshTimer = new Timer(refreshInterval, (ActionEvent e) -> {
            refreshEntities();
        });
        refreshTimer.start();
        refreshEntities();
    }

    @Override
    public int getSize() {
        return entities.size();
    }

    @Override
    public E getElementAt(int index) {
        return entities.get(index);
    }

    private void refreshEntities() {
        new SwingWorker<List<E>, Void>() {

            @Override
            protected List<E> doInBackground() throws Exception {
                List<E> entities = new ArrayList<>();
                int entityCount = controller.getEntityCount();
                for (int rowIndex = 0; rowIndex < entityCount; rowIndex++) {
                    entities.add(controller.getEntityByRowIndex(rowIndex));
                }
                return entities;
            }

            @Override
            protected void done() {
                try {
                    entities = get();
                    fireContentsChanged(this, 0, getSize());
                } catch (InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
                }
            }
        }.execute();
    }

    protected abstract void displayError(SQLException sqlException);
}
