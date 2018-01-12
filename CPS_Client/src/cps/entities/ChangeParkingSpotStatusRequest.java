package cps.entities;

import java.io.Serializable;

import cps.entities.enums.ParkingSpotStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeParkingSpotStatusRequest.
 */
public class ChangeParkingSpotStatusRequest implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;
    
    /** The parking spot. */
    ParkingSpot parkingSpot;
    
    /** The parkinglot name. */
    String parkinglotName;
    
    /** The parking spot status. */
    ParkingSpotStatus parkingSpotStatus;
    
    /**
     * Instantiates a new change parking spot status request.
     *
     * @param parkingSpot the parking spot
     * @param parkinglotName the parkinglot name
     * @param parkingSpotStatus the parking spot status
     */
    public ChangeParkingSpotStatusRequest(ParkingSpot parkingSpot, String parkinglotName,
	    ParkingSpotStatus parkingSpotStatus)
    {
	this.parkingSpot = parkingSpot;
	this.parkinglotName = parkinglotName;
	this.parkingSpotStatus = parkingSpotStatus;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Parking spot: " + parkingSpot + "\nParkinglot name: " + parkinglotName + "\nParking spot Status: "
		+ parkingSpotStatus.toString();
    }
    
    /**
     * Gets the parkinglot name.
     *
     * @return the parkinglot name
     */
    public String getParkinglotName()
    {
	return parkinglotName;
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
     * Gets the parking spot status.
     *
     * @return the parking spot status
     */
    public ParkingSpotStatus getParkingSpotStatus()
    {
	return parkingSpotStatus;
    }
}
