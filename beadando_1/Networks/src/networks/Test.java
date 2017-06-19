
package networks;

/**
 * This is a test (or demo) class
 * @author nemeth.peter
 */
public class Test {

    /**
     * @param args <p>these would be the command line arguments,
     * but now we don't use them</p>
     */
    public static void main(String[] args) {
       Country c = new Country("Ukraine") ;
       
       c.addSubscriber(new Subscriber("Yuri"));
       c.addSubscriber(new Subscriber("Sergei"));
       c.addSubscriber(new Subscriber("Sasha"));
       c.addSubscriber(new Subscriber("Ivan"));
       
       Subscriber sr1 = c.getSubscribers().get(0);
       Subscriber sr2 = c.getSubscribers().get(1);
       Subscriber sr3 = c.getSubscribers().get(2);
       Subscriber sr4 = c.getSubscribers().get(3);
       
       c.addServiceProvider(new ServiceProvider("Kvivstar",60,40,80));
       c.addServiceProvider(new ServiceProvider("MTS",125,35,75));
       c.addServiceProvider(new ServiceProvider("life:)",73,43,93));
       
       ServiceProvider sp1 = c.getServiceProviders().get(0);
       ServiceProvider sp2 = c.getServiceProviders().get(1);
       ServiceProvider sp3 = c.getServiceProviders().get(2);
       
       sp1.addSubscription(new PrePaySubscription(sp1,sr1,101111111));
       sp1.addSubscription(new ContractSubscription(sp1,sr2,102222222));
       sp2.addSubscription(new PrePaySubscription(sp2,sr3,203333333));
       sp3.addSubscription(new ContractSubscription(sp3,sr4,304444444));
       sp2.addSubscription(new PrePaySubscription(sp2,sr4,204444444));
       
       sr1.refill((PrePaySubscription) sr1.getSubscriptions().get(0), 1000000);
       sr3.refill((PrePaySubscription) sr3.getSubscriptions().get(0), 2000000);
       sr4.refill((PrePaySubscription) sr4.getSubscriptions().get(1), 3000000);
       sr4.payScore((ContractSubscription) sr4.getSubscriptions().get(0));
       
       sr1.sendMessage(sr1.getSubscriptions().get(0),302221123,2,new DateTime(2011,12,13,2,43));
       sr1.sendMessage(sr1.getSubscriptions().get(0),302432900,3,new DateTime(2011,12,13,2,47));
       sr2.call(sr2.getSubscriptions().get(0),202455644,5,new DateTime(2012,2,22,10,54));
       sr2.useInternet(sr2.getSubscriptions().get(0),20,new DateTime(2012,2,22,11,59));
       sr3.call(sr3.getSubscriptions().get(0),405435245,3,new DateTime(2013,1,4,23,51));
       sr3.call(sr3.getSubscriptions().get(0),409764464,7,new DateTime(2013,1,12,21,53));
       sr4.call(sr4.getSubscriptions().get(0),764747624,5,new DateTime(2014,3,11,12,10));
       sr4.call(sr4.getSubscriptions().get(1),416241641,3,new DateTime(2014,3,13,7,30));
       
       System.out.println(c.toString());
    }
    
}
