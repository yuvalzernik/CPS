package cps.entities;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class DisabledReport.
 */
public class DisabledReport implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The disabled amount. */
	private int disabledAmount;
	
	/** The active list. */
	private ArrayList<DisabledParkingSpot> activeList;
	
	/** The disabled list. */
	private ArrayList<DisabledParkingSpot> disabledList;
	
	/**
	 * Instantiates a new disabled report.
	 *
	 * @param disabledAmount the disabled amount
	 * @param activeList the active list
	 * @param disabledList the disabled list
	 */
	public DisabledReport(int disabledAmount, ArrayList<DisabledParkingSpot> activeList, ArrayList<DisabledParkingSpot> disabledList)
	{
		this.disabledAmount=disabledAmount;
		
		this.activeList=activeList;
		
		this.disabledList=disabledList;
	}
	
	/**
	 * Gets the disabled amount.
	 *
	 * @return the disabled amount
	 */
	public int getDisabledAmount()
	{
		return disabledAmount;
	}
	
	/**
	 * Gets the active list.
	 *
	 * @return the active list
	 */
	public ArrayList<DisabledParkingSpot> getActiveList()
	{
		return activeList;
	}
	
	/**
	 * Gets the disabled list.
	 *
	 * @return the disabled list
	 */
	public ArrayList<DisabledParkingSpot> getDisabledList()
	{
		return disabledList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString()
    {
		return "Disabled amount: " + disabledAmount;
    }
}
