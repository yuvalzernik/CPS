package cps.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeRatesRequest.
 */
public class ChangeRatesRequest implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The request id. */
    private String requestId;
    
    /** The parkinglot name. */
    private String parkinglotName;
    
    /** The new guest rate. */
    private float newGuestRate;
    
    /** The new in advance rate. */
    private float newInAdvanceRate;
    
    /**
     * Instantiates a new change rates request.
     *
     * @param parkinglotName the parkinglot name
     * @param newGuestRate the new guest rate
     * @param newInAdvanceRate the new in advance rate
     */
    public ChangeRatesRequest(String parkinglotName, float newGuestRate, float newInAdvanceRate)
    {
	this.parkinglotName = parkinglotName;
	this.newGuestRate = newGuestRate;
	this.newInAdvanceRate = newInAdvanceRate;
	
	this.requestId = "Not yet initialized";
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Request id: " + requestId + "\nParkinglot name: " + parkinglotName + "\nNew guest rate: " + newGuestRate
		+ "\nNew in advance rate: " + newInAdvanceRate;
    }
    
    /**
     * Gets the new guest rate.
     *
     * @return the new guest rate
     */
    public float getNewGuestRate()
    {
	return newGuestRate;
    }
    
    /**
     * Gets the new in advance rate.
     *
     * @return the new in advance rate
     */
    public float getNewInAdvanceRate()
    {
	return newInAdvanceRate;
    }
    
    /**
     * Gets the parkinglot name.
     *
     * @return the parkinglot name
     */
    public String getParkinglotName()
    {
	return parkinglotName;
    }
    
    /**
     * Gets the request id.
     *
     * @return the request id
     */
    public String getRequestId()
    {
	return requestId;
    }
    
    /**
     * Sets the request id.
     *
     * @param requestId the new request id
     */
    public void setRequestId(String requestId)
    {
	this.requestId = requestId;
    }
}
