package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import entities.enums.ComplaintStatus;

public class Complaint implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private String complaintId;
    
    private String customerId;
    
    private LocalDateTime fillingDateTime;
    
    private String complaintDetails;
    
    private ComplaintStatus status;
    
    private float compensation;
    
    public Complaint(String customerId, String complaintDetails)
    {
	this.customerId = customerId;
	this.complaintDetails = complaintDetails;
	
	this.complaintId = "Not yet initialized";
	
	this.fillingDateTime = LocalDateTime.now();
	
	this.status = ComplaintStatus.Active;
	
	this.compensation = 0;
    }
    
    public Complaint(String complaintId, String customerId, LocalDateTime fillingDateTime, String complaintDetails,
	    ComplaintStatus status, float compensation)
    {
	this.complaintId = complaintId;
	this.customerId = customerId;
	this.complaintDetails = complaintDetails;
	this.fillingDateTime = fillingDateTime;
	this.status = status;
	this.compensation = compensation;
    }
    
    @Override
    public String toString()
    {
	return "Complaint id: " + complaintId + "\nCustomer id: " + customerId + "\nFilling date time: "
		+ fillingDateTime + "\nComplaint details: " + complaintDetails + "\nComplaint status: " + status
		+ "\nCompensation: " + compensation;
    }
    
    public void setCompensation(float compensation)
    {
	this.compensation = compensation;
    }
    
    public void setComplaintId(String complaintId)
    {
	this.complaintId = complaintId;
    }
    
    public void setStatus(ComplaintStatus status)
    {
	this.status = status;
    }
    
    public float getCompensation()
    {
	return compensation;
    }
    
    public String getComplaintDetails()
    {
	return complaintDetails;
    }
    
    public String getComplaintId()
    {
	return complaintId;
    }
    
    public String getCustomerId()
    {
	return customerId;
    }
    
    public LocalDateTime getFillingDateTime()
    {
	return fillingDateTime;
    }
    
    public ComplaintStatus getStatus()
    {
	return status;
    }
}
