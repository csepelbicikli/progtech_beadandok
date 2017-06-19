
package labirintus.modell;

import labirintus.modell.egyseg.Egyseg;
import java.util.Random;
import labirintus.modell.egyseg.Fal;
import labirintus.modell.egyseg.Padlo;
/**
 *
 * @author nemeth.peter
 */
public class Modell {
    private final int startX;
    private final int startY;
    private final int celX;
    private final int celY;
    private int hosX;
    private int hosY;
    private final int meret;
    
    private Egyseg[][] egysegek;
    
    
    
    public Modell(int meret){
        this.meret=meret;
        startX=0;
        startY=meret-1;
        hosX=startX;
        hosY=startY;
        celX=meret-1;
        celY=0;
        egysegek = new Egyseg[meret][meret];
        //veletlenszeru falgeneralas
        Random rnd = new Random();
        for(int i=0;i<meret;i++){
            for(int j=0;j<meret;j++){
                if((i+j)%2==1){
                    egysegek[i][j]=(rnd.nextBoolean()?new Fal():new Padlo());
                } else if (i%2==0&j%2==0){
                    egysegek[i][j]=new Fal();
                } else {
                    egysegek[i][j]=new Padlo();
                }
            }
        }
        //start legyen gyep
        egysegek[startX][startY]=new Padlo();
        //cel legyen gyep
        egysegek[celX][celY]=new Padlo();
       
    }

    public int getHosX() {
        return hosX;
    }

    public int getHosY() {
        return hosY;
    }
    
    public Egyseg getEgyseg(int x, int y){
        return egysegek[x][y];
    }
    
    public void mozgat(String irany){
        //fel
        if (irany.equals("FEL")&hosY>0){
            if(egysegek[hosX][hosY-1].atjarhato()){
                hosY-=1;
            }
        }
        //le
        if (irany.equals("LE")&hosY<meret-1){
            if(egysegek[hosX][hosY+1].atjarhato()){
                hosY+=1;
            }
        }
        //jobbra
        if (irany.equals("JOBBRA")&hosX<meret-1){
            if(egysegek[hosX+1][hosY].atjarhato()){
                hosX+=1;
            }
        }
        //balra
        if (irany.equals("BALRA")&hosX>0){
            if (egysegek[hosX-1][hosY].atjarhato()){
                hosX-=1;
            }
        }
    }
    
    public boolean vege(){
        return hosX==celX&hosY==celY;
    }
}
