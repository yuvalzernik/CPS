package entities;

import java.io.Serializable;

import entities.enums.ParkingSpotStatus;

public class ChangeParkingSpotStatusRequest implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    ParkingSpot parkingSpot;
    
    String parkinglotName;
    
    ParkingSpotStatus parkingSpotStatus;
    
    public ChangeParkingSpotStatusRequest(ParkingSpot parkingSpot, String parkinglotName,
	    ParkingSpotStatus parkingSpotStatus)
    {
	this.parkingSpot = parkingSpot;
	this.parkinglotName = parkinglotName;
	this.parkingSpotStatus = parkingSpotStatus;
    }
    
    @Override
    public String toString()
    {
	return "Parking spot: " + parkingSpot + "\nParkinglot name: " + parkinglotName + "\nParking spot Status: "
		+ parkingSpotStatus.toString();
    }
    
    public String getParkinglotName()
    {
	return parkinglotName;
    }
    
    public ParkingSpot getParkingSpot()
    {
	return parkingSpot;
    }
    
    public ParkingSpotStatus getParkingSpotStatus()
    {
	return parkingSpotStatus;
    }
}
