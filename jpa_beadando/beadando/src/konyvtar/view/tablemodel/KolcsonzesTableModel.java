

package konyvtar.view.tablemodel;

import konyvtar.model.entity.Kolcsonzes;
import konyvtar.model.entity.Konyv;
import konyvtar.model.entity.Tag;
import hu.elte.inf.prt.db.jpa.controllers.EntityController;
import java.sql.Date;

/**
 *
 * @author nemeth.peter
 */
public class KolcsonzesTableModel extends EntityTableModel<Kolcsonzes>{

    public KolcsonzesTableModel(String[] columnNames, EntityController<Kolcsonzes> controller) {
        super(columnNames, controller);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }
    
    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex){
            case 0: return Integer.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return Date.class;
            case 4: return Date.class;
            default: return Object.class;
        }
    }
    
    @Override
    protected Object getAttributeOfEntity(Kolcsonzes k, int columnIndex) {
        switch(columnIndex){
            case 0: return k.getKolcsonzesId();
            case 1: return (k.getKonyvId().getSzerzo() +": "+k.getKonyvId().getCim());
            case 2: return k.getKonyvtarjegy();
            case 3: return k.getElvitel();
            case 4: return k.getVisszahozatal();
            default: return null;
        }
    }
    

    @Override
    protected void setEntityAttributes(int columnIndex, Kolcsonzes k, Object aValue) {
       
        //mai dátum
        java.util.Date utilDate = new java.util.Date();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        
        switch(columnIndex){    
            case 1:
                k.setKonyvId((Konyv) aValue);
                break;
            case 2:
                k.setKonyvtarjegy((Tag) aValue);
                break;
            
            //elvitel(3), visszahozatal(4) automatikus!! 
            //elvitel esetében az entity konstruktorában kapja meg az aktuális dátumot...
            
            case 4:
                k.setVisszahozatal(sqlDate);
            
            //...míg a visszahozatal itt
            //visszahozatal beállítása esetén tökmindegy mit lökünk be aValue paraméterként
        }
        
    }
    
   

   
    

    
    

}
