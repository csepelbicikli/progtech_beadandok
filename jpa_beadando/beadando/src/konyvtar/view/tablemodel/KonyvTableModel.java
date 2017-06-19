

package konyvtar.view.tablemodel;

import konyvtar.model.entity.Konyv;
import hu.elte.inf.prt.db.jpa.controllers.EntityController;
/**
 *
 * @author nemeth.peter
 */
public class KonyvTableModel extends EntityTableModel<Konyv>{

    public KonyvTableModel(String[] columnNames, EntityController<Konyv> controller) {
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
            case 3: return String.class;
            case 4: return Integer.class;
            case 5: return Integer.class;
            default: return Object.class;
        }
    }
    
    @Override
    protected Object getAttributeOfEntity(Konyv k, int columnIndex) {
        switch(columnIndex){
            case 0: return k.getKonyvId();
            case 1: return k.getSzerzo();
            case 2: return k.getCim();
            case 3: return k.getIsbn();
            case 4: return k.getOsszesPeldany();
            case 5: return k.getSzabadPeldany();
            default: return null;
        }
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Konyv k, Object aValue) {
        
        switch (columnIndex) {
            case 1:
                k.setSzerzo((String) aValue);
                break;
            case 2:
                k.setCim((String) aValue);
                break;
            case 3:
                k.setIsbn((String) aValue);
                break;    
            case 4:
                k.setOsszesPeldany((Integer) aValue);
                break;
        }
        
    }

    

}
