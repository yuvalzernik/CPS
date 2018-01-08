package entities;

import java.io.Serializable;
import java.util.ArrayList;

public class ReservationReport implements Serializable
{
	private static final long serialVersionUID = 1L;
	
	private int reservationAmount;
	
	private int reservationExcersied;
	
	private int reservationCancelled;
	
	private ArrayList<Reservation> guestList;
	
	private ArrayList<Reservation> inAdvanceList;
	
	public ReservationReport(int reservationAmount, int reservationExcersied, int reservationCancelled, ArrayList<Reservation> guestList, ArrayList<Reservation> inAdvanceList)
	{
		this.reservationAmount=reservationAmount;
		
		this.reservationExcersied=reservationExcersied;
		
		this.reservationCancelled=reservationCancelled;
		
		this.guestList=guestList;
		
		this.inAdvanceList=inAdvanceList;
	}
	
	public int getReservationAmount()
	{
		return reservationAmount;
	}
	
	public int getReservationExcersied()
	{
		return reservationExcersied;
	}
	
	public int getReservationCancelled()
	{
		return reservationCancelled;
	}
	
	public ArrayList<Reservation> getGuestList()
	{
		return guestList;
	}
	
	public ArrayList<Reservation> getInAdvanceList()
	{
		return inAdvanceList;
	}
	
	@Override
    public String toString()
    {
		return "Reservation amount: " + reservationAmount + "\nReservations excersied: " + reservationExcersied + "\nReservations cancelled: " + reservationCancelled;
    }
}