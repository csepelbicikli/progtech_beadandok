

package konyvtar.model.entity;

import hu.elte.inf.prt.db.common.entities.EntityWithID;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author nemeth.peter
 */
@Entity
@Table(name = "KONYV")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Konyv.findAll", query = "SELECT k FROM Konyv k"),
    @NamedQuery(name = "Konyv.findByKonyvId", query = "SELECT k FROM Konyv k WHERE k.konyvId = :konyvId"),
    @NamedQuery(name = "Konyv.findBySzerzo", query = "SELECT k FROM Konyv k WHERE k.szerzo = :szerzo"),
    @NamedQuery(name = "Konyv.findByCim", query = "SELECT k FROM Konyv k WHERE k.cim = :cim"),
    @NamedQuery(name = "Konyv.findByIsbn", query = "SELECT k FROM Konyv k WHERE k.isbn = :isbn"),
    @NamedQuery(name = "Konyv.findByKiadas", query = "SELECT k FROM Konyv k WHERE k.kiadas = :kiadas"),
    @NamedQuery(name = "Konyv.findByOsszesPeldany", query = "SELECT k FROM Konyv k WHERE k.osszesPeldany = :osszesPeldany")})
public class Konyv implements Serializable, EntityWithID {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "KONYV_ID")
    private Integer konyvId;
    @Column(name = "SZERZO")
    private String szerzo;
    @Column(name = "CIM")
    private String cim;
    @Column(name = "ISBN")
    private String isbn;
    @Column(name = "KIADAS")
    private Integer kiadas;
    @Column(name = "OSSZES_PELDANY")
    private Integer osszesPeldany;
    @OneToMany(mappedBy = "konyvId")
    private Collection<Kolcsonzes> kolcsonzesCollection;

    public Konyv() {
    }

    public Konyv(Integer konyvId) {
        this.konyvId = konyvId;
    }

    public Integer getKonyvId() {
        return konyvId;
    }

    public void setKonyvId(Integer konyvId) {
        this.konyvId = konyvId;
    }

    public String getSzerzo() {
        return szerzo;
    }

    public void setSzerzo(String szerzo) {
        this.szerzo = szerzo;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
    }
    
    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public Integer getKiadas() {
        return kiadas;
    }

    public void setKiadas(Integer kiadas) {
        this.kiadas = kiadas;
    }

    public Integer getOsszesPeldany() {
        return osszesPeldany;
    }

    public void setOsszesPeldany(Integer osszesPeldany) {
        this.osszesPeldany = osszesPeldany;
    }

    @XmlTransient
    public Collection<Kolcsonzes> getKolcsonzesCollection() {
        return kolcsonzesCollection;
    }

    public void setKolcsonzesCollection(Collection<Kolcsonzes> kolcsonzesCollection) {
        this.kolcsonzesCollection = kolcsonzesCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (konyvId != null ? konyvId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Konyv)) {
            return false;
        }
        Konyv other = (Konyv) object;
        if ((this.konyvId == null && other.konyvId != null) || (this.konyvId != null && !this.konyvId.equals(other.konyvId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return szerzo+": "+cim;
    }

    private static String[] propertyNames = {
        "KonyvId", "Szerzo", "Cim", "Isbn", "OsszesPeldany", "SzabadPeldany"
    };
    
    public static String[] getPropertyNames() {
        return propertyNames;
    }
    
    public int getSzabadPeldany() {
        int cnt=0;//vissza nem hozott kolcsonzesek szama
        for(Kolcsonzes kol: kolcsonzesCollection){
            if(kol.getVisszahozatal()==null){
                ++cnt;
            }
        }
        return (this.osszesPeldany - cnt);
    }
        
    @Override
    public Number getId() {
        return konyvId;
    }

    @Override
    public void setId(Number id) {
        this.konyvId=(Integer) id;
    }
}
