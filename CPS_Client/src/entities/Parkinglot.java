package entities;

import java.io.Serializable;

import entities.enums.ParkinglotStatus;

public class Parkinglot implements Serializable
{
    private static final long serialVersionUID = 1L;

    private final int depth = 3;
    
    private final int height = 3;
    
    private int width;
    
    private String parkinglotName;
    
    private int totalSpotsNumber;
    
    private ParkinglotStatus status;
    
    private float guestRate;
    
    private float inAdvanceRate;
    
    public Parkinglot(String parkinglotName, int width, ParkinglotStatus status, float guestRate,
	    float inAdvanceRate)
    {
	this.parkinglotName = parkinglotName;
	this.width = width;
	this.status = status;
	this.guestRate = guestRate;
	this.inAdvanceRate = inAdvanceRate;
	
	this.totalSpotsNumber = depth * height * width;
    }
    
    public int getDepth()
    {
	return depth;
    }
    
    public float getGuestRate()
    {
	return guestRate;
    }
    
    public int getHeight()
    {
	return height;
    }
    
    public float getInAdvanceRate()
    {
	return inAdvanceRate;
    }
    
    public String getParkinglotName()
    {
	return parkinglotName;
    }
    
    public ParkinglotStatus getStatus()
    {
	return status;
    }
    
    public int getTotalSpotsNumber()
    {
	return totalSpotsNumber;
    }
    
    public int getWidth()
    {
	return width;
    }
    
    @Override
    public String toString()
    {
	return "Parkinglot name: " + parkinglotName + "\nWidth: " + width + "\nStatus: " + status + "\nGuest rate: "
		+ guestRate + "\nInAdvance rate: " + inAdvanceRate + "\nTotal spots: " + totalSpotsNumber;
    }
}
