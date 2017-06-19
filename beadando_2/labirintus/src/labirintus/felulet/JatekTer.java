
package labirintus.felulet;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.swing.JPanel;
import javax.imageio.ImageIO;
import java.io.IOException;
import labirintus.modell.Modell;
import labirintus.modell.egyseg.Fal;
import labirintus.modell.egyseg.Padlo;

/**
 *
 * @author nemeth.peter
 */
public class JatekTer extends JPanel{
    private int meret;
    private Modell modell;

    public Modell getModell() {
        return modell;
    }
    private final BufferedImage padloKep;
    private final BufferedImage falKep;
    private final BufferedImage hosKep;
    
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(int i = modell.getHosX()-1 ; i <= modell.getHosX()+1 ; i++){
            for(int j = modell.getHosY()-1 ; j <= modell.getHosY()+1 ; j++){
                if(i<meret&i>=0&j<meret&j>=0){
                    if (!(modell.getEgyseg(i, j) instanceof Fal)){
                        //amennyiben az 1 sugaru negyzet vmely tagja nem fal 
                        //a belso dupla-for 1 sugarban korerajzol
                        //(magyarul csak akkor latunk at a falakon)
                        for(int k = i-1;k<=i+1;k++){
                            for(int l=j-1;l<=j+1;l++){
                                if (k<meret&k>=0&l<meret&l>=0){
                                    if (modell.getEgyseg(k, l) instanceof Padlo){    
                                        g.drawImage(padloKep,k*16,l*16,null);
                                    } else if (modell.getEgyseg(k, l) instanceof Fal){    
                                        g.drawImage(falKep,k*16,l*16,null);
                                    } 
                                }
                            }
                        }
                        //belso dupla-for vege
                    }
                }
            }
        }
        g.drawImage(hosKep,modell.getHosX()*16,modell.getHosY()*16,null);
    }
    public JatekTer(int meret) throws IOException{
       this.meret=meret;
       modell = new Modell(meret);
       setSize(meret*16,meret*16);
       padloKep=ImageIO.read(new File("gyep.gif"));
       hosKep=ImageIO.read(new File("hos.gif"));
       falKep=ImageIO.read(new File("fal.gif"));
       
       
    }
}
