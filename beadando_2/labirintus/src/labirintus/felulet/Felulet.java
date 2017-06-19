
package labirintus.felulet;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author nemeth.peter
 */
public class Felulet extends JFrame implements KeyListener{
    private static Felulet felulet = null;
    
    private final JatekTer jatekTer;
    
    private final MenuSor menuSor;
    
    private final int meret;
    
    public static void ujJatek(){
        Integer meret=null;
        while(meret==null){
            try{
                meret=Integer.parseInt(JOptionPane.showInputDialog(felulet, "Kerem adja meg a palya meretet (5 es 30 kozott)"));
                if (meret<5|meret>30){
                    meret=null;
                    throw new NumberFormatException();
                }
            }catch(NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(felulet, "Hiba! Kerem szamot adjon meg (5 es 30 kozott)!", "Hiba",JOptionPane.ERROR_MESSAGE);
            }
            
        }
        if (felulet!=null){
            felulet.dispose();
        }
        try{ 
        felulet = new Felulet(meret);
        } catch (IOException ex){
            System.err.println("IO hiba");
        }
        felulet.setVisible(true);
    }
    
    private Felulet(int meret) throws IOException{
        this.meret=meret;
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(600,600);
        setLocationRelativeTo(null);
        this.jatekTer=new JatekTer(meret);
        this.menuSor=new MenuSor();
        setJMenuBar(menuSor);
        add(jatekTer);
        addKeyListener(this);
    }
    
    @Override
    public void keyTyped(KeyEvent e){
        
    }
    @Override
    public void keyPressed(KeyEvent e){
       if (e.getKeyCode() == KeyEvent.VK_RIGHT){
            jatekTer.getModell().mozgat("JOBBRA");
            
        }
        if (e.getKeyCode() == KeyEvent.VK_LEFT){
            jatekTer.getModell().mozgat("BALRA");
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN){
            jatekTer.getModell().mozgat("LE");
        }
        if (e.getKeyCode() == KeyEvent.VK_UP){
            jatekTer.getModell().mozgat("FEL");
        }
        jatekTer.repaint(0,0,0,16*meret,16*meret);
        if (jatekTer.getModell().vege()){
            gratulal();
        }
    }    
    @Override
    public void keyReleased(KeyEvent e){
        // hanyagoljuk
    }
    
    public void gratulal(){
        JOptionPane.showMessageDialog(this,"Gratulalunk. On nyert!","Gratulalunk",JOptionPane.INFORMATION_MESSAGE);
        ujJatek();
    }
    
}
