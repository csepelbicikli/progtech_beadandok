
package networks;

/**
 * This class symbolizes a call given on a specified subscription.
 * @author nemeth.peter
 */
public class Call extends Usage{
    private final int minutes;
    private final int number;
    
   
    /**
     * @param dateTime <p>The date and time of the call(@link DateTime)</p>
     * @param minutes <p>The length of the call in minutes (bigger than zero)</p>
     * @param number <p>Phone number of the called contact</p>
     * @param serviceProvider<p>Current service provider</p>
     */
    public Call(DateTime dateTime , ServiceProvider serviceProvider, int minutes, int number) {
        super(dateTime, serviceProvider);
        this.minutes = minutes;
        this.number = number;
        
    }
    
    /***
     * 
     * @return The cost of the call
     */
    @Override
    public int cost() {
        return (minutes*serviceProvider.getCallRate());
    }
   
    @Override
    public String toString(){
        return (dateTime+": ->"+number+", "+minutes+" minute(s)");
    }
}

