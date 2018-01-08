package entities;

import java.io.Serializable;

public class PerformanceReport implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int membershipAmount;
	
	private int membersMultipleCars;
	
	public PerformanceReport(int membershipAmount, int membersMultipleCars)
	{
		this.membershipAmount=membershipAmount;
		
		this.membersMultipleCars=membersMultipleCars;
	}
	
	public int getMembershipAmount()
	{
		return membershipAmount;
	}
	
	public int getMembersMultipleCars()
	{
		return membersMultipleCars;
	}
	
	@Override
    public String toString()
    {
		return "Membership amount: " + membershipAmount + "\nMembers with multiple cars: " + membersMultipleCars;
    }
}