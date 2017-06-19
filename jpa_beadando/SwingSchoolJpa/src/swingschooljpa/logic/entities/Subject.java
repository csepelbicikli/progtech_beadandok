package swingschooljpa.logic.entities;

import hu.elte.inf.prt.db.jpa.entities.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Subject extends AbstractEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    public final static String[] fieldNames = new String[]{"Title"};
    private String title;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Number id) {
        this.id = id.intValue();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return getTitle();
    }

}
