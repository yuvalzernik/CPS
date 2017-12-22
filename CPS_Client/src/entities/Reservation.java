package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import entities.enums.ReservationStatus;
import entities.enums.ReservationType;

public class Reservation implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String orderId;
    private String customerId;
    private String parkingLot;
    private String carNumber;
    private LocalDate arrivalDate;
    private LocalDate leavingDate;
    private LocalTime arrivalHour;
    private LocalTime leavingHour;
    private ReservationType reservationType;
    private ReservationStatus reservationStatus;
    
    public Reservation(ReservationType reservationType, String customerId, String parkinglot, String carNumber,
	    LocalDate arrivalDate, LocalDate leavingDate, LocalTime arrivalHour, LocalTime leavingHour,
	    ReservationStatus reservationStatus)
    {
	
	this.customerId = customerId;
	this.parkingLot = parkinglot;
	this.carNumber = carNumber;
	this.arrivalDate = arrivalDate;
	this.leavingDate = leavingDate;
	this.arrivalHour = arrivalHour;
	this.leavingHour = leavingHour;
	this.reservationType = reservationType;
	this.reservationStatus = reservationStatus;
	this.orderId = "Not yet initialized";
    }
    
    public String toString()
    {
	return "order ID: " + orderId + "\nReservation Type: " + reservationType + "\nCustomer ID: " + customerId
		+ "\nParkinglot: " + parkingLot + "\nCar Number: " + carNumber + "\nArrival Date: " + arrivalDate
		+ "\nLeaving Date: " + leavingDate + "\nArrival Hour: " + arrivalHour + "\nLeaving Hour: " + leavingHour
		+ "\nReservation status: " + reservationStatus;
    }
    
    public String getOrderId()
    {
	return orderId;
    }
    
    public String getParkinglot()
    {
	return parkingLot;
    }
    
    public String getCarNumber()
    {
	return carNumber;
    }
    
    public LocalDate getArrivalDate()
    {
	return arrivalDate;
    }
    
    public LocalDate getLeavingDate()
    {
	return leavingDate;
    }
    
    public LocalTime getArrivalHour()
    {
	return arrivalHour;
    }
    
    public LocalTime getLeavingHour()
    {
	return leavingHour;
    }
    
    public String getCustomerId()
    {
	return customerId;
    }
    
    public String getParkingLot()
    {
	return parkingLot;
    }
    
    public ReservationStatus getReservationStatus()
    {
	return reservationStatus;
    }
    
    public ReservationType getReservationType()
    {
	return reservationType;
    }
    
    public void setOrderId(String orderId)
    {
	this.orderId = orderId;
    }
}
