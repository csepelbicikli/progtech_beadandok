
package networks;

/**
 * <p>This class represents a <b>fixed time and date combo record</b>.</p>
 * <p>Warning! The elements of the record are constant, you cannot change their 
 * values after you created it.</p>
 * @author nemeth.peter
 */
public class DateTime {
    
    private final int y;
    private final int m;
    private final int d;
    private final int h;
    private final int min;
    /**
     * 
     * @param y Recorded year
     * @param m Recorded month
     * @param d Recorded day
     * @param h Recorded hour(s)
     * @param min Recorded minute(s)
     */
    public DateTime(int y, int m, int d, int h, int min) {
        this.y = y;
        this.m = m;
        this.d = d;
        this.h = h;
        this.min = min;
    }
    /**
     * 
     * @return date and time in <i>YYYY-MM-DD, hh-mm</i> format
     */
    @Override
    public String toString(){
        return (y+"-"+m+"-"+d+", "+h+":"+min);
    }
    
}
