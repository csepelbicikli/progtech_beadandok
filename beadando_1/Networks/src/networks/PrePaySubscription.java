
package networks;

/**
 * This class represents a pre-pay subscription of a mobile network
 * @author nemeth.peter
 */
public class PrePaySubscription extends Subscription{
    private int balance;
    
    /**
     * 
     * @param serviceProvider the current service provider
     * @param owner the owner of the subscription
     * @param number the (phone) number of the subscription
     */        
    public PrePaySubscription(ServiceProvider serviceProvider, Subscriber owner, int number) {
        super(serviceProvider, owner, number);
        this.balance = 0;
        serviceProvider.addSubscription(this);
        owner.addSubscription(this);
        
    }
    
    /**
     * Pay an amount of money (ie. for a call)
     * @param amount Amount of money (greater than zero)
     */
    @Override
    public void pay (int amount){
        serviceProvider.raiseIncome(amount);
        balance-=amount;
    }

    public int getBalance() {
        return balance;
    }
  
  
    @Override
    public String toString(){
        String tmp="Pre-pay subscription: "+number;
        tmp=tmp.concat("\nBalance: "+balance);
        tmp=tmp.concat("\nOwner: "+getOwnerName()+"\n*-*-*\n");
        
        for (Usage usage : usages){
            tmp=tmp.concat(usage.toString());
            tmp=tmp.concat("\n***\n");
        }
        return tmp;
    }

    /**
     * Raise the balance with a specified amount of money
     * @param amount the amount of money (greater than zero)
     */
    public void raiseBalance(int amount) {
        balance += amount;
    }
    
}
