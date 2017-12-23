package entities;

import java.io.Serializable;

public class ChangeRatesRequest implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String requestId;
    
    private String parkinglotName;
    
    private float newGuestRate;
    
    private float newInAdvanceRate;
    
    public ChangeRatesRequest(String parkinglotName, float newGuestRate, float newInAdvanceRate)
    {
	this.parkinglotName = parkinglotName;
	this.newGuestRate = newGuestRate;
	this.newInAdvanceRate = newInAdvanceRate;
	
	this.requestId = "Not yet initialized";
    }
    
    @Override
    public String toString()
    {
	return "Request id: " + requestId + "\nParkinglot name: " + parkinglotName + "\nNew guest rate: " + newGuestRate
		+ "\nNew in advance rate: " + newInAdvanceRate;
    }
    
    public float getNewGuestRate()
    {
	return newGuestRate;
    }
    
    public float getNewInAdvanceRate()
    {
	return newInAdvanceRate;
    }
    
    public String getParkinglotName()
    {
	return parkinglotName;
    }
    
    public String getRequestId()
    {
	return requestId;
    }
    
    public void setRequestId(String requestId)
    {
	this.requestId = requestId;
    }
}
