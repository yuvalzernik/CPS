package cps.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class PerformanceReport.
 */
public class PerformanceReport implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The membership amount. */
	private int membershipAmount;
	
	/** The members multiple cars. */
	private int membersMultipleCars;
	
	/**
	 * Instantiates a new performance report.
	 *
	 * @param membershipAmount the membership amount
	 * @param membersMultipleCars the members multiple cars
	 */
	public PerformanceReport(int membershipAmount, int membersMultipleCars)
	{
		this.membershipAmount=membershipAmount;
		
		this.membersMultipleCars=membersMultipleCars;
	}
	
	/**
	 * Gets the membership amount.
	 *
	 * @return the membership amount
	 */
	public int getMembershipAmount()
	{
		return membershipAmount;
	}
	
	/**
	 * Gets the members multiple cars.
	 *
	 * @return the members multiple cars
	 */
	public int getMembersMultipleCars()
	{
		return membersMultipleCars;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString()
    {
		return "Membership amount: " + membershipAmount + "\nMembers with multiple cars: " + membersMultipleCars;
    }
}