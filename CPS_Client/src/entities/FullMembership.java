package entities;

import java.time.LocalDate;

public class FullMembership extends MembershipBase
{
    private static final long serialVersionUID = 1L;
    
    private String carNumber;
    
    public FullMembership(String id, LocalDate startingDate, LocalDate endingDate, String carNumber)
    {
	super(id, startingDate, endingDate);
	this.carNumber = carNumber;
    }
    
    @Override
    public String toString()
    {
	return "Full Membership: \n\n" + super.toString() + "Car number: " + carNumber;
    }
    
    public String GetCarNumber()
    {
	return carNumber;
    }
}
