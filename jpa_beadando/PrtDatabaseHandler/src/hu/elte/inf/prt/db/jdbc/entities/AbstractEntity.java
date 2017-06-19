package hu.elte.inf.prt.db.jdbc.entities;

public abstract class AbstractEntity extends hu.elte.inf.prt.db.common.entities.AbstractEntity {

    protected Integer id;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Number id) {
        this.id = id.intValue();
    }
}
