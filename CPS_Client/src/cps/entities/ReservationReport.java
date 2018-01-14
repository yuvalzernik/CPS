package cps.entities;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class ReservationReport.
 */
public class ReservationReport implements Serializable
{
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The reservation amount. */
	private int reservationAmount;
	
	/** The reservation excersied. */
	private int reservationExcersied;
	
	/** The reservation cancelled. */
	private int reservationCancelled;
	
	/** The guest list. */
	private ArrayList<Reservation> guestList;
	
	/** The in advance list. */
	private ArrayList<Reservation> inAdvanceList;
	
	/**
	 * Instantiates a new reservation report.
	 *
	 * @param reservationAmount the reservation amount
	 * @param reservationExcersied the reservation excersied
	 * @param reservationCancelled the reservation cancelled
	 * @param guestList the guest list
	 * @param inAdvanceList the in advance list
	 */
	public ReservationReport(int reservationAmount, int reservationExcersied, int reservationCancelled, ArrayList<Reservation> guestList, ArrayList<Reservation> inAdvanceList)
	{
		this.reservationAmount=reservationAmount;
		
		this.reservationExcersied=reservationExcersied;
		
		this.reservationCancelled=reservationCancelled;
		
		this.guestList=guestList;
		
		this.inAdvanceList=inAdvanceList;
	}
	
	/**
	 * Gets the reservation amount.
	 *
	 * @return the reservation amount
	 */
	public int getReservationAmount()
	{
		return reservationAmount;
	}
	
	/**
	 * Gets the reservation excersied.
	 *
	 * @return the reservation excersied
	 */
	public int getReservationExcersied()
	{
		return reservationExcersied;
	}
	
	/**
	 * Gets the reservation cancelled.
	 *
	 * @return the reservation cancelled
	 */
	public int getReservationCancelled()
	{
		return reservationCancelled;
	}
	
	/**
	 * Gets the guest list.
	 *
	 * @return the guest list
	 */
	public ArrayList<Reservation> getGuestList()
	{
		return guestList;
	}
	
	/**
	 * Gets the in advance list.
	 *
	 * @return the in advance list
	 */
	public ArrayList<Reservation> getInAdvanceList()
	{
		return inAdvanceList;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
    public String toString()
    {
		return "Reservation amount: " + reservationAmount + "\nReservations excersied: " + reservationExcersied + "\nReservations cancelled: " + reservationCancelled;
    }
}