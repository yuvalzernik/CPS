package cps.entities;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Hashtable;

import cps.entities.enums.ParkingSpotCondition;

// TODO: Auto-generated Javadoc
/**
 * The Class StatusReport.
 */
public class StatusReport implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The table. */
	private Hashtable<String,ArrayList<ParkingSpotCondition>> table;
	
	/**
	 * Instantiates a new status report.
	 *
	 * @param table the table
	 */
	public StatusReport(Hashtable<String,ArrayList<ParkingSpotCondition>> table)
	{
		this.table=table;
	}
	
	/**
	 * Gets the table.
	 *
	 * @return the table
	 */
	public Hashtable<String,ArrayList<ParkingSpotCondition>> getTable()
	{
		return table;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString()
    {
		return "Table: " + table; 
    }
}
