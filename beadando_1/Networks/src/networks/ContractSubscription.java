
package networks;

/**
 * This class represents a contract subscription of a mobile network
 * @author nemeth.peter
 */
public class ContractSubscription extends Subscription{
    /**
     * Score already reached (greater than zero). You should pay it
     * to your provider later (eg. monthly)
     */
    private int score;
    
    /**
     * 
     * @param serviceProvider the current service provider
     * @param owner the owner of the subscription
     * @param number the (phone) number of the subscription
     */
    public ContractSubscription(ServiceProvider serviceProvider, Subscriber owner, int number) {
        super(serviceProvider, owner, number);
        this.score = 0;
        serviceProvider.addSubscription(this);
        owner.addSubscription(this);
    }
    /**
     * NOTE: Unlike the other situation at the @link PrePaySubscription.pay() ,
     * here you don't really pay to the provider, the rates only fill your score.
     * You will pay to the provider later (@link payScore()).
     * @param amount Amount of money
     */
    @Override
    public void pay(int amount){
        score+=amount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public String toString(){
        String tmp="Contract subscription: "+number;
        tmp=tmp.concat("\nScore: "+score);
        tmp=tmp.concat("\nOwner: "+getOwnerName()+"\n*-*-*\n");
        
        for (Usage usage : usages){
            tmp=tmp.concat(usage.toString());
            tmp=tmp.concat("\n***\n");
        }
        return tmp;
    }
    
}
