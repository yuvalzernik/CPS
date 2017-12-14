package application;

public class MemberIdentifyingInformation
{
    private String carNumber;
    
    private String subscriptionId;
    
    public MemberIdentifyingInformation(String subscriptionId, String carNumber)
    {
	this.carNumber = carNumber;
	this.subscriptionId = subscriptionId;
    }
    
    public String GetCarNumber()
    {
	return carNumber;
    }
    
    public String GetSubscriptionId()
    {
	return subscriptionId;
    }
}
