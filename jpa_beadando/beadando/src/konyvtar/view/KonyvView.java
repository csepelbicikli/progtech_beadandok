
package konyvtar.view;

import konyvtar.controller.KonyvController;
import konyvtar.model.entity.Konyv;
import konyvtar.view.tablemodel.KonyvTableModel;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;

/**
 *
 * @author nemeth.peter
 */
public class KonyvView extends JFrame {
    
    private JTable table;
    private KonyvTableModel model;
    
   // private ListSelectionListener selectionListener = new ListSelectionListener() {
   //     @Override
   //     public void valueChanged(ListSelectionEvent e) {
   //        
   //     }
   // };

    public KonyvView() {
        this.setTitle("Konyvek");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setupTableModel();
        setupTable();
        this.setLayout(new BoxLayout(this.getContentPane(),BoxLayout.Y_AXIS)); 
        this.pack();
        this.setLocationRelativeTo(null);
        
    }

    private void setupTableModel() {
        model = new KonyvTableModel(Konyv.getPropertyNames(),KonyvController.getInstance());
    }

    private void setupTable() {
        table = new JTable(model);
        table.setPreferredScrollableViewportSize(
                new Dimension(800, 150)
        );
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.setAutoCreateRowSorter(true);
        
        this.add(new JScrollPane(table));
        JButton visszaGomb = new JButton("Vissza");
        visszaGomb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame foMenu = new FoMenu();
                foMenu.setVisible(true);
                KonyvView.this.setVisible(false);
                KonyvView.this.dispose();
            }
        });
        this.add(visszaGomb);
    }
    

    
}
