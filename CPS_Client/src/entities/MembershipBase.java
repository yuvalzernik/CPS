package entities;

import java.io.Serializable;
import java.time.LocalDate;

public abstract class MembershipBase implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    String subscriptionId;

    protected String customerId;
    
    protected LocalDate startingDate;
    
    protected LocalDate endingDate;
    
    public MembershipBase(String id, LocalDate startingDate, LocalDate endingDate)
    {
	this.customerId = id;
	this.startingDate = startingDate;
	this.endingDate = endingDate;
	
	subscriptionId = "Not yet initialized";
    }
    
    @Override
    public String toString()
    {
	return "subscriptionId: " + subscriptionId + "\nCustomer ID: " + customerId + "\nStaring date: " + startingDate + "\nEnding date: " + endingDate + "\n";
    }
    
    public String GetCustomerId()
    {
	return customerId;
    }
    
    public LocalDate GetStartingDate()
    {
	return startingDate;
    }
    
    public LocalDate GetEndingDate()
    {
	return endingDate;
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
