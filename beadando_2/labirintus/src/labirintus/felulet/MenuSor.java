
package labirintus.felulet;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import labirintus.felulet.figyelo.UjJatekFigyelo;

/**
 *
 * @author nemeth.peter
 */
public class MenuSor extends JMenuBar{
    private final JMenu jatekMenu;
    private final JMenuItem ujJatek;
    public MenuSor(){ 
        this.ujJatek = new JMenuItem("Uj jatek");
        this.jatekMenu = new JMenu("Jatek");
        ujJatek.addActionListener(new UjJatekFigyelo());
        add(jatekMenu);
        jatekMenu.add(ujJatek);
    }
}
