

package konyvtar.model.entity;

import hu.elte.inf.prt.db.common.entities.EntityWithID;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author nemeth.peter
 */
@Entity
@Table(name = "KOLCSONZES")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kolcsonzes.findAll", query = "SELECT k FROM Kolcsonzes k"),
    @NamedQuery(name = "Kolcsonzes.findByKolcsonzesId", query = "SELECT k FROM Kolcsonzes k WHERE k.kolcsonzesId = :kolcsonzesId"),
    @NamedQuery(name = "Kolcsonzes.findByElvitel", query = "SELECT k FROM Kolcsonzes k WHERE k.elvitel = :elvitel"),
    @NamedQuery(name = "Kolcsonzes.findByVisszahozatal", query = "SELECT k FROM Kolcsonzes k WHERE k.visszahozatal = :visszahozatal")})
public class Kolcsonzes implements Serializable, EntityWithID {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "KOLCSONZES_ID")
    private Integer kolcsonzesId;
    @Column(name = "ELVITEL")
    @Temporal(TemporalType.DATE)
    private Date elvitel;
    @Column(name = "VISSZAHOZATAL")
    @Temporal(TemporalType.DATE)
    private Date visszahozatal;
    @JoinColumn(name = "KONYV_ID", referencedColumnName = "KONYV_ID")
    @ManyToOne
    private Konyv konyvId;
    @JoinColumn(name = "KONYVTARJEGY", referencedColumnName = "KONYVTARJEGY")
    @ManyToOne
    private Tag konyvtarjegy;

    public Kolcsonzes() {
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        this.elvitel=sqlDate;
        this.visszahozatal=null;
    }

    public Kolcsonzes(Integer kolcsonzesId) {
        this();
        this.kolcsonzesId = kolcsonzesId;
        
    }

    public Integer getKolcsonzesId() {
        return kolcsonzesId;
    }

    public void setKolcsonzesId(Integer kolcsonzesId) {
        this.kolcsonzesId = kolcsonzesId;
    }

    public Date getElvitel() {
        return elvitel;
    }

    public void setElvitel(Date elvitel) {
        this.elvitel = elvitel;
    }

    public Date getVisszahozatal() {
        return visszahozatal;
    }

    public void setVisszahozatal(Date visszahozatal) {
        this.visszahozatal = visszahozatal;
    }

    public Konyv getKonyvId() {
        return konyvId;
    }

    public void setKonyvId(Konyv konyvId) {
        this.konyvId = konyvId;
    }

    public Tag getKonyvtarjegy() {
        return konyvtarjegy;
    }

    public void setKonyvtarjegy(Tag konyvtarjegy) {
        this.konyvtarjegy = konyvtarjegy;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (kolcsonzesId != null ? kolcsonzesId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Kolcsonzes)) {
            return false;
        }
        Kolcsonzes other = (Kolcsonzes) object;
        if ((this.kolcsonzesId == null && other.kolcsonzesId != null) || (this.kolcsonzesId != null && !this.kolcsonzesId.equals(other.kolcsonzesId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "beadando.konyvtar.model.entity.Kolcsonzes[ kolcsonzesId=" + kolcsonzesId + " ]";
    }

    private static String[] propertyNames = {
        "KolcsonzesId", "KonyvId", "Konyvtarjegy", "Elvitel", "Visszahozatal"
    };
    
    
    public static String[] getPropertyNames() {
        return propertyNames;
    }

    @Override
    public Number getId() {
        return this.kolcsonzesId;
    }

    @Override
    public void setId(Number id) {
        this.kolcsonzesId=(Integer) id;
    }
}
