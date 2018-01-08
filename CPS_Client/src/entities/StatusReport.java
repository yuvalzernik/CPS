package entities;

import java.io.Serializable;
import java.util.ArrayList;

import java.util.Hashtable;

import entities.enums.ParkingSpotCondition;

public class StatusReport implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private Hashtable<String,ArrayList<ParkingSpotCondition>> table;
	
	public StatusReport(Hashtable<String,ArrayList<ParkingSpotCondition>> table)
	{
		this.table=table;
	}
	
	public Hashtable<String,ArrayList<ParkingSpotCondition>> getTable()
	{
		return table;
	}
	
	@Override
    public String toString()
    {
		return "Table: " + table; 
    }
}
