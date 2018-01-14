package cps.entities;

// TODO: Auto-generated Javadoc
/**
 * The Class GuestIdentifyingInformation.
 */
public class GuestIdentifyingInformation
{
    
    /** The car number. */
    private String carNumber;
    
    /** The order id. */
    private String orderId;
    
    /**
     * Instantiates a new guest identifying information.
     *
     * @param orderId the order id
     * @param carNumber the car number
     */
    public GuestIdentifyingInformation(String orderId, String carNumber)
    {
	this.carNumber = carNumber;
	this.orderId = orderId;
    }
    
    /**
     * Gets the car number.
     *
     * @return the string
     */
    public String GetCarNumber()
    {
	return carNumber;
    }
    
    /**
     * Gets the order id.
     *
     * @return the string
     */
    public String GetOrderId()
    {
	return orderId;
    }
}
