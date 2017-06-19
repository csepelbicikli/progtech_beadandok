package hu.elte.inf.prt.db.common.entities;

import java.io.Serializable;

public abstract class AbstractEntity implements EntityWithID, Serializable {

    protected static final long serialVersionUID = 1L;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (getId() != null ? getId().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (!(object.getClass().equals(this.getClass()))) {
            return false;
        }
        EntityWithID other = (EntityWithID) object;
        return (this.getId() != null || other.getId() == null) && (this.getId() == null || this.getId().equals(other.getId()));
    }

    @Override
    public String toString() {
        return getClass().getName() + "[ id = " + getId() + " ]";
    }

}
