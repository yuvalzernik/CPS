package entities;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class MembershipBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    protected String subscriptionId;

    protected String customerId;
    
    protected LocalDate startDate;
    
    protected LocalDate expiryDate;
    
    public MembershipBase(String id, LocalDate startingDate, LocalDate endingDate)
    {
	this.customerId = id;
	this.startDate = startingDate;
	this.expiryDate = endingDate;
	
	subscriptionId = "Not yet initialized";
    }
    
    @Override
    public String toString()
    {
	return "subscriptionId: " + subscriptionId + "\nCustomer ID: " + customerId + "\nStar date: " + startDate + "\nExpiry date: " + expiryDate + "\n";
    }
    
    public String GetCustomerId()
    {
	return customerId;
    }
    
    public LocalDate GetStartDate()
    {
	return startDate;
    }
    
    public LocalDate getExpiryDate()
    {
	return expiryDate;
    }
    
    public String GetSubscriptionId()
    {
	return subscriptionId;
    }
    
    public void SetSubscriptionId(String subscriptionId)
    {
	this.subscriptionId = subscriptionId;
    }
    
}
