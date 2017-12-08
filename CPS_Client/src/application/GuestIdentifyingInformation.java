package application;

public class GuestIdentifyingInformation
{
    private String carNumber;
    
    private String orderId;
    
    public GuestIdentifyingInformation(String orderId, String carNumber)
    {
	this.carNumber = carNumber;
	this.orderId = orderId;
    }
    
    public String GetCarNumber()
    {
	return carNumber;
    }
    
    public String GetOrderId()
    {
	return orderId;
    }
}
