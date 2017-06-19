

package konyvtar.view;

import konyvtar.main.BeadandoMain;
import konyvtar.controller.KolcsonzesController;
import konyvtar.controller.KonyvController;
import konyvtar.controller.TagController;
import konyvtar.model.entity.Kolcsonzes;
import konyvtar.model.entity.Konyv;
import konyvtar.model.entity.Tag;
import konyvtar.view.comboboxmodel.EntityComboBoxModel;
import konyvtar.view.tablemodel.KolcsonzesTableModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 *
 * @author nemeth.peter
 */
public class KolcsonzesView extends JFrame {
    
    private JTable table;
    private JButton ujKolcsGomb;
    private JButton visszahozGomb;
    private KolcsonzesTableModel model;
    
    private ListSelectionListener selectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            visszahozGomb.setEnabled(table.getSelectedRowCount()==1);
        }
    };

    public KolcsonzesView() {
        this.setTitle("Kolcsonzesek");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       
        setupTableModel();
        setupTable();
        setupButtons();
        
        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS)); 
        this.pack();
        this.setLocationRelativeTo(null);
        
    }

    private void setupTableModel() {
        model = new KolcsonzesTableModel(Kolcsonzes.getPropertyNames(),KolcsonzesController.getInstance());
    }
    
    private void setupTable() {
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension(800, 150)
        );
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        this.add(new JScrollPane(table));
    }
    
    private void setupButtons() {
        ujKolcsGomb = new JButton("Uj kolcsonzes felvetele");
        ujKolcsGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    if (KonyvController.getInstance().getEntityCount()==0){
                        throw new IOException("Nincs konyv a konyvtarban!");
                    }
                    if (TagController.getInstance().getEntityCount()==0){
                        throw new IOException("Nincs tagja a konyvtarnak!");
                    }
                    JComboBox comboBox1 = new JComboBox(new EntityComboBoxModel(KonyvController.getInstance()));
                    comboBox1.setEditable(false);
                    JOptionPane.showMessageDialog( rootPane, comboBox1, "Valasszon ki egy konyvet", JOptionPane.QUESTION_MESSAGE);
                    Konyv konyv=(Konyv) comboBox1.getSelectedItem();
                    JComboBox comboBox2 = new JComboBox(new EntityComboBoxModel(TagController.getInstance()));
                    comboBox2.setEditable(false);
                    JOptionPane.showMessageDialog( rootPane, comboBox2, "Valasszon ki egy konyvtartagot", JOptionPane.QUESTION_MESSAGE);
                    Tag tag=(Tag) comboBox2.getSelectedItem();
                    if (konyv==null|tag==null){
                        throw new IOException("Hiba! On nem valasztott ki tagot vagy konyvet!");
                    }
                    if (konyv.getSzabadPeldany()<=0){
                        throw new IOException("Hiba! Az on altal kivalsztott konyvbol mindet kikolcsonoztek!");
                    }
                    if (tag.getKolcsonzesekSzama()>=4){
                        throw new IOException("Hiba! Az on altal kivalasztott tag tobb konyvet nem kolcsonozhet!");
                    }
                    Kolcsonzes k= new Kolcsonzes();
                    k.setKonyvId(konyv);
                    k.setKonyvtarjegy(tag);
                    model.addNewEntity(k);
                } catch(IOException ex){
                    BeadandoMain.showError(ex.getMessage());
                }
            } 
        });
        this.add(ujKolcsGomb);
        visszahozGomb = new JButton("Visszahozas feljegyzese");
        visszahozGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int kivSor = table.convertRowIndexToModel(table.getSelectedRows()[0]);
                if (model.getValueAt(kivSor,4)==null){
                    model.setValueAt(null, kivSor,4 );
                // a fenti muvelet  a mai datumot (ld. KolcsonzesTableModel), a null csak disznek van  
                }else{
                    BeadandoMain.showError("Hiba! Az on altal kivalasztott konyv mar vissza van hozva!");
                }
            }
        });
        this.add(visszahozGomb);
        visszahozGomb.setEnabled(false);
        table.getSelectionModel().addListSelectionListener(selectionListener);
        JButton visszaGomb = new JButton("Vissza");
        visszaGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame foMenu = new FoMenu();
                foMenu.setVisible(true);
                KolcsonzesView.this.setVisible(false);
                KolcsonzesView.this.dispose();
            }
        });
        this.add(visszaGomb);
    }
}
