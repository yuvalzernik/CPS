package cps.entities;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class RemoveCarRequest.
 */
public class RemoveCarRequest implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The parkinglot. */
    private String parkinglot;
    
    /** The car number. */
    private String carNumber;
    
    /**
     * Instantiates a new removes the car request.
     *
     * @param parkinglot the parkinglot
     * @param carNumber the car number
     */
    public RemoveCarRequest(String parkinglot, String carNumber)
    {
	this.parkinglot = parkinglot;
	this.carNumber = carNumber;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Parking lot: " + parkinglot + "\nCar number: " + carNumber;
    }
    
    /**
     * Gets the car number.
     *
     * @return the car number
     */
    public String getCarNumber()
    {
	return carNumber;
    }
    
    /**
     * Gets the parkinglot.
     *
     * @return the parkinglot
     */
    public String getParkinglot()
    {
	return parkinglot;
    }
    
}
