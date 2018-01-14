package cps.entities;

import java.io.Serializable;

import cps.entities.enums.ParkinglotStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class ChangeParkinglotStatusRequest.
 */
public class ChangeParkinglotStatusRequest implements Serializable
{
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /** The parkinglot name. */
    private String parkinglotName;
    
    /** The parkinglot status. */
    private ParkinglotStatus parkinglotStatus;
    
    /**
     * Instantiates a new change parkinglot status request.
     *
     * @param parkinglotName the parkinglot name
     * @param parkinglotStatus the parkinglot status
     */
    public ChangeParkinglotStatusRequest(String parkinglotName, ParkinglotStatus parkinglotStatus)
    {
	this.parkinglotName = parkinglotName;
	this.parkinglotStatus = parkinglotStatus;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString()
    {
	return "Parkinglot name: " + parkinglotName + "\nStatus: " + parkinglotStatus;
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
     * Gets the parkinglot status.
     *
     * @return the parkinglot status
     */
    public ParkinglotStatus getParkinglotStatus()
    {
	return parkinglotStatus;
    }
}
