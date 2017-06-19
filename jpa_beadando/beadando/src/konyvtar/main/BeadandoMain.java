

package konyvtar.main;

import konyvtar.view.FoMenu;
import java.awt.EventQueue;
import javax.swing.JOptionPane;

/**
 *
 * @author nemeth.peter
 */
public class BeadandoMain {

    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                new FoMenu().setVisible(true);
            }
        });
    }
    
    public static void showError(String error){
        JOptionPane.showMessageDialog(null, error,"Hiba!",JOptionPane.ERROR_MESSAGE);
    }

}
