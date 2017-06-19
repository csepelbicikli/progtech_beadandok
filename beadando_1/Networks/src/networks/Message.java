
package networks;

/**
 * This class symbolizes a message sent to a specified subscription.
 * @author nemeth.peter
 */
public class Message extends Usage{
    private int length;
    private int number;
    /**
     * 
     * @param dateTime Date and time (@link DateTime) when the message was sent.
     * @param serviceProvider The current service provider (it's used to get the message rate).
     * @param length Length of the message.
     * @param number The number of the recipient.
     */
    public Message(DateTime dateTime, ServiceProvider serviceProvider, int length, int number){
        super(dateTime,serviceProvider);
        this.length = length;
        this.number = number;
    }
    
    /***
     * 
     * @return The cost of the message
     */
    @Override
    public int cost() {
        return (length*serviceProvider.getMessageRate());
    }
    
    
    @Override
    public String toString(){
        return (dateTime+": ->"+number+", "+length+" character(s)");
    }
}
