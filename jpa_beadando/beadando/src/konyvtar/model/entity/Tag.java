

package konyvtar.model.entity;

import hu.elte.inf.prt.db.common.entities.EntityWithID;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
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
@Table(name = "TAG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Tag.findAll", query = "SELECT t FROM Tag t"),
    @NamedQuery(name = "Tag.findByKonyvtarjegy", query = "SELECT t FROM Tag t WHERE t.konyvtarjegy = :konyvtarjegy"),
    @NamedQuery(name = "Tag.findByNev", query = "SELECT t FROM Tag t WHERE t.nev = :nev"),
    @NamedQuery(name = "Tag.findByCim", query = "SELECT t FROM Tag t WHERE t.cim = :cim")})
public class Tag implements Serializable , EntityWithID{
    private static final long serialVersionUID = 1L;
    

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE)
    @Column(name = "KONYVTARJEGY")
    private Integer konyvtarjegy;
    @Column(name = "NEV")
    private String nev;
    @Column(name = "CIM")
    private String cim;
    @OneToMany(mappedBy = "konyvtarjegy", cascade=CascadeType.PERSIST, orphanRemoval=true)
    private Collection<Kolcsonzes> kolcsonzesCollection;

    public Tag() {
    }

    public Tag(Integer konyvtarjegy) {
        this.konyvtarjegy = konyvtarjegy;
    }

    public Integer getKonyvtarjegy() {
        return konyvtarjegy;
    }

    public void setKonyvtarjegy(Integer konyvtarjegy) {
        this.konyvtarjegy = konyvtarjegy;
    }

    public String getNev() {
        return nev;
    }

    public void setNev(String nev) {
        this.nev = nev;
    }

    public String getCim() {
        return cim;
    }

    public void setCim(String cim) {
        this.cim = cim;
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
        hash += (konyvtarjegy != null ? konyvtarjegy.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Tag)) {
            return false;
        }
        Tag other = (Tag) object;
        if ((this.konyvtarjegy == null && other.konyvtarjegy != null) || (this.konyvtarjegy != null && !this.konyvtarjegy.equals(other.konyvtarjegy))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return nev+" (" +konyvtarjegy+")";
    }
    
    //SAJAT KIEGESZITES ELEJE
    private static String[] propertyNames = {
        "Konyvtarjegy", "Nev", "Cim", "KolcsonzesekSzama"
    };
    
    
    public static String[] getPropertyNames() {
        return propertyNames;
    }
    

    public int getKolcsonzesekSzama(){
        int cnt=0;//vissza nem hozott kolcsonzesek szama
        for(Kolcsonzes kol: kolcsonzesCollection){
            if(kol.getVisszahozatal()==null){
                ++cnt;
            }
        }
        return (cnt);
    }
        
    //SAJAT KIEGESZITES VEGE

    @Override
    public Number getId(){
        return konyvtarjegy;
    }

    @Override
    public void setId(Number id) {
        this.konyvtarjegy=(Integer) id;
    }

   

}
