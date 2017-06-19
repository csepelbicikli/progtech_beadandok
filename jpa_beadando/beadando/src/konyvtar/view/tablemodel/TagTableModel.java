

package konyvtar.view.tablemodel;

import konyvtar.controller.KolcsonzesController;
import konyvtar.model.entity.Kolcsonzes;
import konyvtar.model.entity.Tag;
import hu.elte.inf.prt.db.jpa.controllers.EntityController;

/**
 *
 * @author nemeth.peter
 */
public class TagTableModel extends EntityTableModel<Tag>{

    public TagTableModel(String[] columnNames, EntityController<Tag> controller) {
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
            case 3: return Integer.class;
            default: return Object.class;
        }
    }
    
    
    @Override
    protected Object getAttributeOfEntity(Tag t, int columnIndex) {
        switch(columnIndex){
            case 0: return t.getKonyvtarjegy();
            case 1: return t.getNev();
            case 2: return t.getCim();
            case 3: return t.getKolcsonzesekSzama();
            default: return null;
        }
    }

    @Override
    protected void setEntityAttributes(int columnIndex, Tag t, Object aValue) {
        
        switch(columnIndex){
            case 1:
                t.setNev((String) aValue);
                break;
            case 2:
                t.setCim((String) aValue);
                break;
        }
       
    }

    
}
