
package networks;

/**
 * This class symbolizes an ocassion of using internet.
 * @author nemeth.peter
 */
public class InternetUse extends Usage{
    private final int dataAmount;
    
    /**
     * 
     * @param dateTime Date and time (@link DateTime) when the internet was used
     * @param serviceProvider The current service provider 
     *                        (it's used to get the rate for using internet).
     * @param dataAmount The amount of data requested in kilobytes.
     */
    public InternetUse(DateTime dateTime, ServiceProvider serviceProvider, int dataAmount) {
        super(dateTime, serviceProvider);
        this.dataAmount = dataAmount;
    }
    
    /***
     * 
     * @return The cost of using internet
     */
    @Override
    public int cost() {
        return (dataAmount*serviceProvider.getDataRate());
    }
    
    /***
     * 
     * @return <p>Informations about the internet usage in string format 
     * for the @link Subscription.toString() method</p> 
     */
    @Override
    public String toString(){
        return (dateTime+", "+dataAmount+" kilobyte(s)");
    }
    
}
