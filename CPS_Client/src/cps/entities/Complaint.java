package cps.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import cps.entities.enums.ComplaintStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class Complaint.
 */
public class Complaint implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The complaint id. */
    private String complaintId;
    
    /** The customer id. */
    private String customerId;
    
    /** The filling date time. */
    private LocalDateTime fillingDateTime;
    
    /** The complaint details. */
    private String complaintDetails;
    
    /** The status. */
    private ComplaintStatus status;
    
    /** The compensation. */
    private float compensation;
    
    /**
     * Instantiates a new complaint.
     *
     * @param customerId the customer id
     * @param complaintDetails the complaint details
     */
    public Complaint(String customerId, String complaintDetails)
    {
	this.customerId = customerId;
	this.complaintDetails = complaintDetails;
	
	this.complaintId = "Not yet initialized";
	
	this.fillingDateTime = LocalDateTime.now();
	
	this.status = ComplaintStatus.Active;
	
	this.compensation = 0;
    }
    
    /**
     * Instantiates a new complaint.
     *
     * @param complaintId the complaint id
     * @param customerId the customer id
     * @param fillingDateTime the filling date time
     * @param complaintDetails the complaint details
     * @param status the status
     * @param compensation the compensation
     */
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
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Complaint id: " + complaintId + "\nCustomer id: " + customerId + "\nFilling date time: "
		+ fillingDateTime + "\nComplaint details: " + complaintDetails + "\nComplaint status: " + status
		+ "\nCompensation: " + compensation;
    }
    
    /**
     * Sets the compensation.
     *
     * @param compensation the new compensation
     */
    public void setCompensation(float compensation)
    {
	this.compensation = compensation;
    }
    
    /**
     * Sets the complaint id.
     *
     * @param complaintId the new complaint id
     */
    public void setComplaintId(String complaintId)
    {
	this.complaintId = complaintId;
    }
    
    /**
     * Sets the status.
     *
     * @param status the new status
     */
    public void setStatus(ComplaintStatus status)
    {
	this.status = status;
    }
    
    /**
     * Gets the compensation.
     *
     * @return the compensation
     */
    public float getCompensation()
    {
	return compensation;
    }
    
    /**
     * Gets the complaint details.
     *
     * @return the complaint details
     */
    public String getComplaintDetails()
    {
	return complaintDetails;
    }
    
    /**
     * Gets the complaint id.
     *
     * @return the complaint id
     */
    public String getComplaintId()
    {
	return complaintId;
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
    
    /**
     * Gets the filling date time.
     *
     * @return the filling date time
     */
    public LocalDateTime getFillingDateTime()
    {
	return fillingDateTime;
    }
    
    /**
     * Gets the status.
     *
     * @return the status
     */
    public ComplaintStatus getStatus()
    {
	return status;
    }
}
