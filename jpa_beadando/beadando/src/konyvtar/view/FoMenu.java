

package konyvtar.view;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JPanel;

/**
 *
 * @author nemeth.peter
 */
public class FoMenu extends JFrame{
    private final JPanel panel; 
    public FoMenu(){
        super();
        this.setTitle("Konyvtarkezelo fomenu");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize(400, 400);
        this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
       
        panel = new JPanel();
        panel.setLayout(new GridLayout(10,1));
        JButton konyvViewGomb = new JButton("Konyvek...");
        JButton tagViewGomb = new JButton("Tagok...");
        JButton kolcsonzesViewGomb = new JButton("Kolcsonzesek...");
        panel.add(konyvViewGomb);
        panel.add(tagViewGomb);
        panel.add(kolcsonzesViewGomb);
        konyvViewGomb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame konyvView = new KonyvView();
                konyvView.setVisible(true);
                FoMenu.this.setVisible(false);
                FoMenu.this.dispose();
            }
        });
        tagViewGomb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame tagView = new TagView();
                tagView.setVisible(true);
                FoMenu.this.setVisible(false);
                FoMenu.this.dispose();
            }
        });
        kolcsonzesViewGomb.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame kolcsonzesView = new KolcsonzesView();
                kolcsonzesView.setVisible(true);
                FoMenu.this.setVisible(false);
                FoMenu.this.dispose();
            }
        });
        add(panel);
    }
}
/*
szavak szerkesztese
szavak kikerdezese
nyelvek szerkesztese
*/