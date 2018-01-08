package entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import entities.enums.ParkingSpotStatus;

public class DisabledParkingSpot implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private String parkingLotName;
	
	private ParkingSpot parkingSpot;
	
	private LocalDateTime startDateTime;
	
	private LocalDateTime endDateTime;
	
	private ParkingSpotStatus status;
	
	public DisabledParkingSpot(String parkingLotName, ParkingSpot parkingSpot, LocalDateTime startDateTime, LocalDateTime endDateTime, ParkingSpotStatus status)
	{
		this.parkingLotName=parkingLotName;
		
		this.parkingSpot=parkingSpot;
		
		this.startDateTime=startDateTime;
		
		this.endDateTime=endDateTime;
		
		this.status=status;
	}
	
	public String getParkingLotName()
	{
		return parkingLotName;
	}
	
	public ParkingSpot getParkingSpot()
	{
		return parkingSpot;
	}
	
	public LocalDateTime getStartDateTime()
	{
		return startDateTime;
	}
	
	public LocalDateTime getEndDateTime()
	{
		return endDateTime;
	}
	
	public ParkingSpotStatus getStatus()
	{
		return status;
	}
	
	@Override
    public String toString()
    {
		return "Parking lot name: " + parkingLotName + "\nParking Spot: " + parkingSpot + "\n Start DateTime: " + startDateTime + "\nEnd DateTime: " + endDateTime;
    }
}
