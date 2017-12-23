package entities;

import java.io.Serializable;

public class ChangeRatesResponse implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String requestId;
    
    private boolean isApproved;
    
    public ChangeRatesResponse(String requestId, boolean isApproved)
    {
	this.requestId = requestId;
	this.isApproved = isApproved;
    }
    
    @Override
    public String toString()
    {
	return "Change rates request id: " + requestId + "\nApproved: " + isApproved;
    }
    
    public String getRequestId()
    {
	return requestId;
    }
    
    public boolean getIsApproved()
    {
	return isApproved;
    }
}
