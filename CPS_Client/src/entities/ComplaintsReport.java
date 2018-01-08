package entities;

import java.io.Serializable;

public class ComplaintsReport implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int complaintAmount;
	
	private int handledComplaints;
	
	public ComplaintsReport(int complaintAmount,int handledComplaints)
	{
		this.complaintAmount=complaintAmount;
		
		this.handledComplaints=handledComplaints;
	}
	
	public int getComplaintAmount()
	{
		return complaintAmount;
	}
	
	public int getHandledComplaints()
	{
		return handledComplaints;
	}
	
	@Override
    public String toString()
    {
		return "Complaint amount: " + complaintAmount + "\nHandled complaints: " + handledComplaints;
    }
}