package hu.elte.inf.prt.swing.tablemodels;

import hu.elte.inf.prt.db.jdbc.controllers.EntityController;
import hu.elte.inf.prt.db.common.entities.EntityWithID;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.swing.SwingWorker;
import javax.swing.Timer;
import javax.swing.table.AbstractTableModel;

public abstract class AbstractJdbcEntityTableModel<E extends EntityWithID> extends AbstractTableModel {

    protected final String[] columnNames;
    protected final int refreshInterval;
    protected final Timer refreshTimer;
    protected List<E> entities;
    protected EntityController<E> controller;

    public AbstractJdbcEntityTableModel(String[] columnNames, EntityController<E> controller) {
        this.controller = controller;
        this.columnNames = columnNames;
        this.refreshInterval = 60 * 1000;
        entities = new ArrayList<>();
        refreshTimer = new Timer(refreshInterval, (ActionEvent e) -> {
            reloadEntities();
        });
        reloadEntities();
        refreshTimer.start();

    }

    protected E getEntityAtRow(int rowIndex) {
        return entities.get(rowIndex);
    }

    @Override
    public int getRowCount() {
        return entities.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        E entity = getEntityAtRow(rowIndex);
        return getAttributeOfEntity(entity, columnIndex);
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public void setValueAt(Object aValue, final int rowIndex, int columnIndex) {
        final E entity = getEntityAtRow(rowIndex);
        setEntityAttributes(columnIndex, entity, aValue);
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                controller.updateEntity(entity, rowIndex);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    reloadEntities();
                } catch (InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
                }
            }

        }.execute();
    }

    protected void addNewEntity(final E entity) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                controller.addEntity(entity);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    reloadEntities();
                } catch (InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
                }
            }

        }.execute();
    }

    public void deleteEntity(final int rowIndex) {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                controller.deleteEntity(rowIndex);
                return null;
            }

            @Override
            protected void done() {
                try {
                    get();
                    reloadEntities();
                } catch (InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
                }
            }

        }.execute();
    }

    protected final void reloadEntities() {
        new SwingWorker<List<E>, Void>() {
            @Override
            protected List<E> doInBackground() throws Exception {
                List<E> entities = controller.getEntities();
                return entities;
            }

            @Override
            protected void done() {
                try {
                    entities = get();
                    fireTableDataChanged();
                } catch (InterruptedException | ExecutionException ex) {
                    displayError((SQLException) ex.getCause());
                }
            }

        }.execute();
    }

    protected abstract void displayError(SQLException sqlException);

    protected abstract Object getAttributeOfEntity(E entity, int columnIndex);

    protected abstract void setEntityAttributes(int columnIndex, final E entity, Object aValue);

    public abstract void addNewEntity();
}
