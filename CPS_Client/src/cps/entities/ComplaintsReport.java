package cps.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class ComplaintsReport.
 */
public class ComplaintsReport implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The complaint amount. */
	private int complaintAmount;
	
	/** The handled complaints. */
	private int handledComplaints;
	
	/**
	 * Instantiates a new complaints report.
	 *
	 * @param complaintAmount the complaint amount
	 * @param handledComplaints the handled complaints
	 */
	public ComplaintsReport(int complaintAmount,int handledComplaints)
	{
		this.complaintAmount=complaintAmount;
		
		this.handledComplaints=handledComplaints;
	}
	
	/**
	 * Gets the complaint amount.
	 *
	 * @return the complaint amount
	 */
	public int getComplaintAmount()
	{
		return complaintAmount;
	}
	
	/**
	 * Gets the handled complaints.
	 *
	 * @return the handled complaints
	 */
	public int getHandledComplaints()
	{
		return handledComplaints;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString()
    {
		return "Complaint amount: " + complaintAmount + "\nHandled complaints: " + handledComplaints;
    }
}