package entities;

import java.io.Serializable;

public class CreditCustomerRequest implements Serializable
{
    private static final long serialVersionUID = 1L;

    private float credit;
    
    private String customerId;
    
    public CreditCustomerRequest(String customerId, float credit)
    {
	this.credit = credit;
	this.customerId = customerId;
    }
    
    public float getCredit()
    {
	return credit;
    }
    
    public String getCustomerId()
    {
	return customerId;
    }
    
    @Override
    public String toString()
    {
	return "customer ID: " + customerId + " Credit: " + credit;
    }
}
