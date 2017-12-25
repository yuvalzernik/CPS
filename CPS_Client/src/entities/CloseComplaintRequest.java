package entities;

import java.io.Serializable;

public class CloseComplaintRequest implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String complaintId;
    
    private float compensation;
    
    public CloseComplaintRequest(String complaintId, float compensation)
    {
	this.complaintId = complaintId;
	this.compensation = compensation;
    }
    
    @Override
    public String toString()
    {
	return "Complaint Id: " + complaintId + "\nCompensation: " + compensation;
    }
    
    public float getCompensation()
    {
	return compensation;
    }
    
    public String getComplaintId()
    {
	return complaintId;
    }
}
