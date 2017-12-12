package application.Models;

import java.time.LocalDate;

public abstract class MembershipBase
{
    protected String id;
    
    protected LocalDate startingDate;
    
    protected LocalDate endingDate;
    
    public MembershipBase(String id, LocalDate startingDate, LocalDate endingDate)
    {
	this.id = id;
	this.startingDate = startingDate;
	this.endingDate = endingDate;
    }
    
    @Override
    public String toString()
    {
	return "Id: " + id + "\nStaring date: " + startingDate + "\nEnding date: " + endingDate + "\n";
    }
    
    public String GetId()
    {
	return id;
    }
    
    public LocalDate GetStartingDate()
    {
	return startingDate;
    }
    
    public LocalDate GetEndingDate()
    {
	return endingDate;
    }
    
}
