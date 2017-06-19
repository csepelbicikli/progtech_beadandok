package hu.elte.inf.prt.db.jdbc.controllers;

import hu.elte.inf.prt.db.common.entities.EntityWithID;
import java.sql.SQLException;
import java.util.List;

public interface EntityController<E extends EntityWithID> {

    public int getEntityCount() throws SQLException;

    public List<E> getEntities() throws SQLException;

    public E getEntityById(int id) throws SQLException;

    public E getEntityByRowIndex(int rowIndex) throws SQLException;

    public void addEntity(E entity) throws SQLException;

    public void deleteEntity(int index) throws SQLException;

    public void updateEntity(E entity, int index) throws SQLException;
}
