
package networks;

import java.util.LinkedList;
import java.util.List;

/**
 * This class represents a subscriber.
 * @author nemeth.peter
 */
public class Subscriber {
    private String name;
    private List<Subscription> subscriptions;

    public Subscriber(String name){
        this.name = name;
        this.subscriptions = new LinkedList<>();
    }
    
    /**
     * Adds a subscription to the list of subscriptions 
     * (NOTE: do not use this to create subscriptions)
     * @param subscription  An existing(!) subscription
     */
     public void addSubscription(Subscription subscription){
        subscriptions.add(subscription);
    }

    public List<Subscription> getSubscriptions() {
        return subscriptions;
    }

    public String getName() {
        return name;
    }
    
    /**
     * A method for making a call from a specified subscription
     * @param subscription An existing(!) subscription
     * @param number Phone number of the called contact
     * @param minutes The length of the call in minutes (bigger than zero)
     * @param dateTime The date and time of the call(@link DateTime)
     */ 
    public void call(Subscription subscription, int number, int minutes, DateTime dateTime){
         if (subscription instanceof PrePaySubscription){
             PrePaySubscription testSubscription = (PrePaySubscription) subscription;
            
             Usage testUse = new Call(dateTime,subscription.getServiceProvider(),minutes,number);
             
             if (testUse.cost()>testSubscription.getBalance()){
                 System.out.println("ERROR: "+name+":"+testSubscription.getNumber()+":"+"Couldn\'t make call because of low balance on your subscription");
                 return;
             }  
         }
         Call c1=new Call(dateTime,subscription.getServiceProvider(),minutes,number);
         subscription.addUsage(c1);
         subscription.pay(c1.cost());
    }
    
    /**
     * A method for using internet from a specified subscription
     * @param subscription An existing(!) subscription.
     * @param dataAmount The amount of data requested in kilobytes.
     * @param dateTime Date and time (@link DateTime) when the internet was used.
     */
    public void useInternet(Subscription subscription, int dataAmount, DateTime dateTime){
         if (subscription instanceof PrePaySubscription){
             PrePaySubscription testSubscription = (PrePaySubscription) subscription;
             Usage testUse = new InternetUse(dateTime,subscription.getServiceProvider(),dataAmount);
             
             if (testUse.cost()>testSubscription.getBalance()){
                 System.out.println("ERROR: "+name+":"+testSubscription.getNumber()+":"+"Couldn\'t use internet because of low balance on your subscription");
                 return;
             }  
         }
         InternetUse i1=new InternetUse(dateTime,subscription.getServiceProvider(),dataAmount);
         subscription.addUsage(i1);
         subscription.pay(i1.cost());
    }
    
    /**
     * 
     * @param subscription An existing(!) subscription.
     * @param number Phone number of the recipient
     * @param length Length of the message (characters)
     * @param dateTime Date and time (@link DateTime) when the message was sent.
     */
    public void sendMessage(Subscription subscription, int number, int length, DateTime dateTime){
         if (subscription instanceof PrePaySubscription){
             PrePaySubscription testSubscription = (PrePaySubscription) subscription;
             Usage testUse = new Message(dateTime,subscription.getServiceProvider(),length, number);
             if (testUse.cost()>testSubscription.getBalance()){
                 System.out.println("ERROR: "+name+":"+testSubscription.getNumber()+":"+"Couldn\'t send message because of low balance on your subscription");
                 return;
             }  
         }
         Message m1=new Message(dateTime,subscription.getServiceProvider(),length,number);
         subscription.addUsage(m1);
         subscription.pay(m1.cost());
    }
    
    /**
     * Refills an amount of money for a pre-pay subscription of the subscriber.
     * @param prePaySubscription A pre-pay subscription of the subscriber
     * @param amount Amount of money (greater than zero)
     */
    public void refill(PrePaySubscription prePaySubscription, int amount){
        prePaySubscription.raiseBalance(amount);
    }
    
    /**
     * Pay the score to the provider.
     * NOTE: I simplified the situation a little. 
     * When you pay, you pay all the score you have.
     * @param contractSubscription A contract subscription of the subscriber
     */
    public void payScore(ContractSubscription contractSubscription){
        contractSubscription.getServiceProvider().raiseIncome(contractSubscription.getScore());
        contractSubscription.setScore(0);
    }
}
