//KESZ
package networks;

/**
 * An abstract class to represent operations with a mobile network subscription.
 * @author nemeth.peter
 */
public abstract class Usage {
    protected final ServiceProvider serviceProvider;
    protected final DateTime dateTime;
    public abstract int cost();
    public abstract String toString();

    public Usage(DateTime dateTime, ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        this.dateTime = dateTime;
    }
   
}
