package hu.elte.inf.prt.db.jdbc.controllers;

import hu.elte.inf.prt.db.common.entities.EntityWithID;
import hu.elte.inf.prt.db.jdbc.ConnectionFactory;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractEntityController<E extends EntityWithID> implements EntityController<E> {

    private final String FULL_SELECT_SQL;
    private final String SELECT_COUNT_SQL;
    private final String SELECT_BY_ID_SQL;

    public AbstractEntityController(String TABLE_NAME) {
        FULL_SELECT_SQL = "SELECT * FROM " + TABLE_NAME;
        SELECT_COUNT_SQL = "SELECT COUNT(*) AS CNT FROM " + TABLE_NAME;
        SELECT_BY_ID_SQL = FULL_SELECT_SQL + " WHERE ID = ";
    }

    protected interface RunnableOnResultSet {

        public void run(ResultSet rs) throws SQLException;
    }

    protected void doOnResultSet(String sql, int resultSetType, int resultSetConcurrency, RunnableOnResultSet todo) throws SQLException {
        try (
                Connection connection = ConnectionFactory.getInstance().getConnection();
                Statement statement = connection.createStatement(resultSetType, resultSetConcurrency);
                ResultSet rs = statement.executeQuery(sql);) {
            todo.run(rs);
        }
    }

    private class IntegerHolder {

        private int intt;

        public void setIntt(int intt) {
            this.intt = intt;
        }

        public int getIntt() {
            return intt;
        }
    }

    @Override
    public int getEntityCount() throws SQLException {
        final IntegerHolder count = new IntegerHolder();
        doOnResultSet(SELECT_COUNT_SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            rs.next();
            count.setIntt(rs.getInt("CNT"));
        });
        return count.getIntt();
    }

    @Override
    public List<E> getEntities() throws SQLException {
        final List<E> entities = new ArrayList<>();
        doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            while (rs.next()) {
                E entity = newEntity();
                entity.setId(rs.getInt("ID"));
                setEntityAttributes(entity, rs);
                entities.add(entity);
            }
        });
        return entities;
    }

    @Override
    public E getEntityById(int id) throws SQLException {
        final E entity = newEntity();
        doOnResultSet(SELECT_BY_ID_SQL + id, ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            rs.next();
            entity.setId(rs.getInt("ID"));
            setEntityAttributes(entity, rs);
        });
        return entity;
    }

    @Override
    public E getEntityByRowIndex(final int rowIndex) throws SQLException {
        final E entity = newEntity();
        doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY, (ResultSet rs) -> {
            rs.absolute(rowIndex + 1);
            entity.setId(rs.getInt("ID"));
            setEntityAttributes(entity, rs);
        });
        return entity;
    }

    @Override
    public void addEntity(final E entity) throws SQLException {
        doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
            rs.moveToInsertRow();
            rs.updateInt("ID", ConnectionFactory.getInstance().obtainNewId());
            getEntityAttributes(rs, entity);
            rs.insertRow();
        });
    }

    @Override
    public void deleteEntity(final int index) throws SQLException {
        doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
            rs.absolute(index + 1);
            rs.deleteRow();
        });
    }

    @Override
    public void updateEntity(final E entity, final int index) throws SQLException {
        doOnResultSet(FULL_SELECT_SQL, ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE, (ResultSet rs) -> {
            rs.absolute(index + 1);
            rs.updateInt("ID", entity.getId().intValue());
            getEntityAttributes(rs, entity);
            rs.updateRow();
        });
    }

    protected abstract E newEntity();

    protected abstract void setEntityAttributes(E entity, ResultSet resultSet) throws SQLException;

    protected abstract void getEntityAttributes(ResultSet resultSet, E entity) throws SQLException;
}
