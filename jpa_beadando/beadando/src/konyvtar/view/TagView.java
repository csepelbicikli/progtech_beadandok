

package konyvtar.view;



import konyvtar.main.BeadandoMain;
import konyvtar.controller.TagController;
import konyvtar.model.entity.Tag;
import konyvtar.view.tablemodel.TagTableModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.JButton;
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
public class TagView extends JFrame{
    
    private JTable table;
    private TagTableModel model;
    private JButton modositTagGomb;
    private JButton torolTagGomb;
    private ListSelectionListener selectionListener = new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            modositTagGomb.setEnabled(table.getSelectedRowCount()==1);
            torolTagGomb.setEnabled(table.getSelectedRowCount()==1); 
        }
    };

    public TagView() {
        setTitle("Tagok");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupTableModel();
        setupTable();
        setupButtons();
        setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS)); 
        pack();
        setLocationRelativeTo(null);
    }

    private void setupTableModel() {
        model = new TagTableModel(Tag.getPropertyNames(),TagController.getInstance());
    }

    private void setupTable() {
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension(800, 150)
        );
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        table.getSelectionModel().addListSelectionListener(selectionListener);
        this.add(new JScrollPane(table));
    }

    private void setupButtons(){
        JButton ujTagGomb = new JButton("Uj tag felvetele");
        ujTagGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Tag t = new Tag(); 
                t.setNev((JOptionPane.showInputDialog(rootPane, "Kerem adja meg az illeto nevet.","<nev>",JOptionPane.QUESTION_MESSAGE,null,null,"nev")).toString());
                t.setCim((JOptionPane.showInputDialog(rootPane, "Kerem adja meg az illeto cimet.","<cim>",JOptionPane.QUESTION_MESSAGE,null,null,"cim")).toString());
                model.addNewEntity(t);
            }
        });
        this.add(ujTagGomb);
        modositTagGomb = new JButton("Tag szerkesztese");
        modositTagGomb.setEnabled(false);
        modositTagGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    String nev=(String) JOptionPane.showInputDialog(rootPane, "Kerem adja meg a leendo tag nevet.","<nev>",JOptionPane.QUESTION_MESSAGE,null,null,"nev");
                    String cim=(String) JOptionPane.showInputDialog(rootPane, "Kerem adja meg a leendo tag cimet.","<cim>",JOptionPane.QUESTION_MESSAGE,null,null,"cim");
                    if (nev==null | cim==null || nev.equals("") || cim.equals("")){
                        throw new IOException("Hiba az adatmegadasban");
                    }
                    model.setValueAt(nev, table.convertRowIndexToModel(table.getSelectedRows()[0]), 1);
                    model.setValueAt(cim, table.convertRowIndexToModel(table.getSelectedRows()[0]), 2);
                } catch (IOException ex){
                    BeadandoMain.showError(ex.getMessage());
                }
            }
        });
        this.add(modositTagGomb);
        torolTagGomb = new JButton("Tag torlese");
        torolTagGomb.setEnabled(false);
        torolTagGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                Integer kolcsonzesekSzama = (Integer) model.getValueAt(table.getSelectedRows()[0], 3);
                if(kolcsonzesekSzama==0){
                    int selectedModelRow=table.convertRowIndexToModel(table.getSelectedRows()[0]);
                    model.deleteEntity(selectedModelRow);
                }else{
                    BeadandoMain.showError("Nem torolheto mert van meg kint levo kolcsonzese");
                }
            }
        });
        this.add(torolTagGomb);
        JButton visszaGomb = new JButton("Vissza");
        visszaGomb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame foMenu = new FoMenu();
                foMenu.setVisible(true);
                TagView.this.setVisible(false);
                TagView.this.dispose();
            }
        });
        this.add(visszaGomb);
    }
}
