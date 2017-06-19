package hu.elte.inf.prt.db.jpa.controllers;

import hu.elte.inf.prt.db.common.entities.EntityWithID;
import hu.elte.inf.prt.db.jpa.controllers.exceptions.NonexistentEntityException;
import java.util.List;

public interface EntityController<E extends EntityWithID> {

    public int getEntityCount();

    public List<E> getEntities();

    public E getEntityById(int id);

    public E getEntityByRowIndex(int rowIndex);

    public void addEntity(E entity);

    public void deleteEntity(int index) throws NonexistentEntityException;

    public void updateEntity(E entity) throws Exception;
}
