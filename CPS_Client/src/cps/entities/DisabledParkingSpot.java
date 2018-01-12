package cps.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import cps.entities.enums.ParkingSpotStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class DisabledParkingSpot.
 */
public class DisabledParkingSpot implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The parking lot name. */
	private String parkingLotName;
	
	/** The parking spot. */
	private ParkingSpot parkingSpot;
	
	/** The start date time. */
	private LocalDateTime startDateTime;
	
	/** The end date time. */
	private LocalDateTime endDateTime;
	
	/** The status. */
	private ParkingSpotStatus status;
	
	/**
	 * Instantiates a new disabled parking spot.
	 *
	 * @param parkingLotName the parking lot name
	 * @param parkingSpot the parking spot
	 * @param startDateTime the start date time
	 * @param endDateTime the end date time
	 * @param status the status
	 */
	public DisabledParkingSpot(String parkingLotName, ParkingSpot parkingSpot, LocalDateTime startDateTime, LocalDateTime endDateTime, ParkingSpotStatus status)
	{
		this.parkingLotName=parkingLotName;
		
		this.parkingSpot=parkingSpot;
		
		this.startDateTime=startDateTime;
		
		this.endDateTime=endDateTime;
		
		this.status=status;
	}
	
	/**
	 * Gets the parking lot name.
	 *
	 * @return the parking lot name
	 */
	public String getParkingLotName()
	{
		return parkingLotName;
	}
	
	/**
	 * Gets the parking spot.
	 *
	 * @return the parking spot
	 */
	public ParkingSpot getParkingSpot()
	{
		return parkingSpot;
	}
	
	/**
	 * Gets the start date time.
	 *
	 * @return the start date time
	 */
	public LocalDateTime getStartDateTime()
	{
		return startDateTime;
	}
	
	/**
	 * Gets the end date time.
	 *
	 * @return the end date time
	 */
	public LocalDateTime getEndDateTime()
	{
		return endDateTime;
	}
	
	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public ParkingSpotStatus getStatus()
	{
		return status;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString()
    {
		return "Parking lot name: " + parkingLotName + "\nParking Spot: " + parkingSpot + "\n Start DateTime: " + startDateTime + "\nEnd DateTime: " + endDateTime;
    }
}
