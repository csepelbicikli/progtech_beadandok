package swingschooljpa.logic.entities;

import hu.elte.inf.prt.db.jpa.entities.AbstractEntity;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SubjTeach extends AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    public final static String[] fieldNames = new String[]{"Teacher", "Subject", "Group"};
    @ManyToOne
    private Teacher teacher;
    @ManyToOne
    private Subject subject;
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

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Groupp getGroupp() {
        return groupp;
    }

    public void setGroupp(Groupp groupp) {
        this.groupp = groupp;
    }

}
