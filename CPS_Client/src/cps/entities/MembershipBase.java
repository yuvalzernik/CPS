package cps.entities;

import java.io.Serializable;
import java.time.LocalDate;

// TODO: Auto-generated Javadoc
/**
 * The Class MembershipBase.
 */
public abstract class MembershipBase implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The subscription id. */
    protected String subscriptionId;

    /** The customer id. */
    protected String customerId;
    
    /** The start date. */
    protected LocalDate startDate;
    
    /** The expiry date. */
    protected LocalDate expiryDate;
    
    /**
     * Instantiates a new membership base.
     *
     * @param id the id
     * @param startingDate the starting date
     * @param endingDate the ending date
     */
    public MembershipBase(String id, LocalDate startingDate, LocalDate endingDate)
    {
	this.customerId = id;
	this.startDate = startingDate;
	this.expiryDate = endingDate;
	
	subscriptionId = "Not yet initialized";
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "subscriptionId: " + subscriptionId + "\nCustomer ID: " + customerId + "\nStart date: " + startDate + "\nExpiry date: " + expiryDate + "\n";
    }
    
    /**
     * Gets the customer id.
     *
     * @return the string
     */
    public String GetCustomerId()
    {
	return customerId;
    }
    
    /**
     * Gets the start date.
     *
     * @return the local date
     */
    public LocalDate GetStartDate()
    {
	return startDate;
    }
    
    /**
     * Gets the expiry date.
     *
     * @return the expiry date
     */
    public LocalDate getExpiryDate()
    {
	return expiryDate;
    }
    
    /**
     * Gets the subscription id.
     *
     * @return the string
     */
    public String GetSubscriptionId()
    {
	return subscriptionId;
    }
    
    /**
     * Sets the subscription id.
     *
     * @param subscriptionId the subscription id
     */
    public void SetSubscriptionId(String subscriptionId)
    {
	this.subscriptionId = subscriptionId;
    }
    
}
