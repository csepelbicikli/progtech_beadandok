
package labirintus.felulet.figyelo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author nemeth.peter
 */
public class UjJatekFigyelo implements ActionListener{
    @Override
    public void actionPerformed(ActionEvent e){
        labirintus.felulet.Felulet.ujJatek();
    }
}
