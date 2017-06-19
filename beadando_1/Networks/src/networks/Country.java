
package networks;

import java.util.List;
import java.util.LinkedList;

/**
 * This class represents a country
 * @author nemeth.peter
 */
public class Country {
    private final String name;
    private List<ServiceProvider> serviceProviders;
    private List<Subscriber> subscribers;

    /**
     * Creates a new country
     * @param name Name of the country 
     */
    public Country(String name) {
        this.name = name;
        serviceProviders = new LinkedList<>();
        subscribers = new LinkedList<>();
      
    }

  
    
    public void addSubscriber(Subscriber subscriber){
        subscribers.add(subscriber);
    }
    
    public List<Subscriber> getSubscribers() {
        return subscribers;
    }
    
    public void addServiceProvider(ServiceProvider serviceProvider){
        serviceProviders.add(serviceProvider);
    }
    
    public List<ServiceProvider> getServiceProviders() {
        return serviceProviders;
    }
    
    @Override
    public String toString(){
        String tmp="Country: "+name+"\n-_-_-_-_-_-_-_-\n";
        for (ServiceProvider serviceProvider : serviceProviders){
            tmp=tmp.concat(serviceProvider.toString());
            tmp=tmp.concat("\n-----\n");
        }
        return tmp;
    }
}
