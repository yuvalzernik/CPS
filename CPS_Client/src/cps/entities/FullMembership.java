package cps.entities;

import java.time.LocalDate;

// TODO: Auto-generated Javadoc
/**
 * The Class FullMembership.
 */
public class FullMembership extends MembershipBase
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The car number. */
    private String carNumber;
    
    /**
     * Instantiates a new full membership.
     *
     * @param id the id
     * @param startingDate the starting date
     * @param endingDate the ending date
     * @param carNumber the car number
     */
    public FullMembership(String id, LocalDate startingDate, LocalDate endingDate, String carNumber)
    {
	super(id, startingDate, endingDate);
	this.carNumber = carNumber;
    }
    
    /* (non-Javadoc)
     * @see cps.entities.MembershipBase#toString()
     */
    @Override
    public String toString()
    {
	return "Full Membership: \n\n" + super.toString() + "Car number: " + carNumber;
    }
    
    /**
     * Gets the car number.
     *
     * @return the string
     */
    public String GetCarNumber()
    {
	return carNumber;
    }
}
