package application.Models;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PartialMembership extends MembershipBase
{
    String parkinglot;
    
    ArrayList<String> carList;
    
    LocalTime exitTime;
    
    public PartialMembership(String id, LocalDate startingDate, LocalDate endingDate, String parkinglot,
	    ArrayList<String> carList, LocalTime exitTime)
    {
	super(id, startingDate, endingDate);
	
	this.parkinglot = parkinglot;
	this.carList = carList;
	this.exitTime = exitTime;
    }
    
    public String GetParkinglot()
    {
	return parkinglot;
    }
    
    public ArrayList<String> GetCarList()
    {
	return carList;
    }
    
    public LocalTime GetExitTime()
    {
	return exitTime;
    }
    
    @Override
    public String toString()
    {
	String carListString = "";
	
	for (String car : carList)
	{
	    carListString += ("\n" + car);
	}
	
	return super.toString() + "Parkinglot: " + parkinglot + "\nExit time: "
		+ exitTime + carListString;
    }
}
