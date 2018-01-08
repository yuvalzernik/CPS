package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class DisabledReport implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int disabledAmount;
	
	private ArrayList<DisabledParkingSpot> activeList;
	
	private ArrayList<DisabledParkingSpot> disabledList;
	
	public DisabledReport(int disabledAmount, ArrayList<DisabledParkingSpot> activeList, ArrayList<DisabledParkingSpot> disabledList)
	{
		this.disabledAmount=disabledAmount;
		
		this.activeList=activeList;
		
		this.disabledList=disabledList;
	}
	
	public int getDisabledAmount()
	{
		return disabledAmount;
	}
	
	public ArrayList<DisabledParkingSpot> getActiveList()
	{
		return activeList;
	}
	
	public ArrayList<DisabledParkingSpot> getDisabledList()
	{
		return disabledList;
	}
	
	@Override
    public String toString()
    {
		return "Disabled amount: " + disabledAmount;
    }
}
