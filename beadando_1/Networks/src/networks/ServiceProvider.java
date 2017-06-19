
package networks;

import java.util.LinkedList;
import java.util.List;

/**
 * This class symbolizes a mobile service provider.
 * @author nemeth.peter
 */
public class ServiceProvider {
    private String name;
    private int income;
    private List<Subscription> subscriptions;
    private int callRate;
    private int dataRate;
    private int messageRate;

    /**
     * 
     * @param name Name of the service provider
     * @param callRate The call rate of the service provider (per minute).
     * @param dataRate The data (internet) rate of the service provider (per kilobyte).
     * @param messageRate The message rate of the service provider (per character)
     */
    public ServiceProvider(String name, int callRate, int dataRate, int messageRate) {
        this.name = name;
        this.callRate = callRate;
        this.dataRate = dataRate;
        this.messageRate = messageRate;
        income=0;
        subscriptions = new LinkedList<>();
    }
    
    

    public int getCallRate() {
        return callRate;
    }

    public int getDataRate() {
        return dataRate;
    }

    public int getMessageRate() {
        return messageRate;
    }
    
    public void setCallRate(int callRate) {
        this.callRate = callRate;
    }

    public void setDataRate(int dataRate) {
        this.dataRate = dataRate;
    }

    public void setMessageRate(int messageRate) {
        this.messageRate = messageRate;
    }
    /**
     * Raises the income with an amount of money
     * @param amount Amount of money (greater than zero)
     */
    public void raiseIncome(int amount){
        income+=amount;
    }
    
    @Override
    public String toString(){
        String tmp="Service provider: "+name+"\n";
        tmp=tmp.concat("Income: "+income+"\n.*.-.*.-.*.\n");
        for (Subscription subscription : subscriptions){
            tmp=tmp.concat(subscription.toString());
            tmp=tmp.concat("\n***\n");
        }
        return tmp;
    }
    /**
     * Adds a subscription to the list of subscriptions 
     * @param subscription  A new subscription
     */
    public void addSubscription(Subscription subscription){
        subscriptions.add(subscription);
    }
    
    
}
