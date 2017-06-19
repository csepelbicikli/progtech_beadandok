package swingschooljpa.logic.entities;

import hu.elte.inf.prt.db.jpa.entities.AbstractEntity;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Student extends AbstractEntity{

    @OneToMany(mappedBy = "student")
    private List<Mark> marks;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    public final static String[] fieldNames = new String[]{"First name", "Last name", "Group"};
    private String firstName;
    private String lastName;
    @ManyToOne
    private Groupp groupp;

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Number id) {
        this.id = id.intValue();
    }

    public List<Mark> getMarks() {
        return marks;
    }

    public void setMarks(List<Mark> marks) {
        this.marks = marks;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Groupp getGroupp() {
        return groupp;
    }

    public void setGroupp(Groupp groupp) {
        this.groupp = groupp;
    }

    @Override
    public String toString() {
        return getFirstName() + " " + getLastName();
    }

}
