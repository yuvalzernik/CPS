package cps.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class CreditCustomerRequest.
 */
public class CreditCustomerRequest implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The credit. */
    private float credit;
    
    /** The customer id. */
    private String customerId;
    
    /**
     * Instantiates a new credit customer request.
     *
     * @param customerId the customer id
     * @param credit the credit
     */
    public CreditCustomerRequest(String customerId, float credit)
    {
	this.credit = credit;
	this.customerId = customerId;
    }
    
    /**
     * Gets the credit.
     *
     * @return the credit
     */
    public float getCredit()
    {
	return credit;
    }
    
    /**
     * Gets the customer id.
     *
     * @return the customer id
     */
    public String getCustomerId()
    {
	return customerId;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "customer ID: " + customerId + " Credit: " + credit;
    }
}
