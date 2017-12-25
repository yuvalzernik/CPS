package entities;

import java.io.Serializable;

import entities.enums.ParkinglotStatus;

public class ChangeParkinglotStatusRequest implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String parkinglotName;
    
    private ParkinglotStatus parkinglotStatus;
    
    public ChangeParkinglotStatusRequest(String parkinglotName, ParkinglotStatus parkinglotStatus)
    {
	this.parkinglotName = parkinglotName;
	this.parkinglotStatus = parkinglotStatus;
    }
    
    @Override
    public String toString()
    {
	return "Parkinglot name: " + parkinglotName + "\nStatus: " + parkinglotStatus;
    }
    
    public String getParkinglotName()
    {
	return parkinglotName;
    }
    
    public ParkinglotStatus getParkinglotStatus()
    {
	return parkinglotStatus;
    }
}
