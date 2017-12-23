package entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class PartialMembership extends MembershipBase
{
    private static final long serialVersionUID = 1L;

    private String parkinglot;
    
    private ArrayList<String> carList;
    
    private LocalTime exitTime;
    
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
	return super.toString() + "Parkinglot: " + parkinglot + "\nExit time: "
		+ exitTime + "\n" + CarListString();
    }
    
    public String CarListString()
    {
	String carListString = "";
	for(String  car : carList)
	{
	    carListString += (car +" ,");
	}
	
	// trick : 
	
	if(carListString != "")
	{
	    carListString += '$';
	    carListString = carListString.replace(" ,$", "");
	}
	
	return carListString;
    }
}
