
package networks;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a subscription of a service provider that 
 * belons to one subscriber.
 * @author nemeth.peter
 */
public abstract class Subscription {
    protected int number;
    protected List<Usage> usages;
    private Subscriber owner;
    protected ServiceProvider serviceProvider;
    
    public Subscription(ServiceProvider serviceProvider, Subscriber owner, int number){
        
        this.serviceProvider = serviceProvider;
        this.owner = owner;
        this.number = number;
        usages = new LinkedList<>();
        
    }

    public ServiceProvider getServiceProvider() {
        return serviceProvider;
    }
    
    public abstract void pay(int amount);

    public int getNumber() {
        return number;
    }
    
    
    
    public String getOwnerName(){
        return owner.getName();
    }
    public abstract String toString();
   
    public void addUsage (Usage usage){
        usages.add(usage);
    }
}
