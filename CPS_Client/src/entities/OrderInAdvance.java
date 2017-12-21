package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class OrderInAdvance implements Serializable {

	private static final long serialVersionUID = 1L;
	private String customerId;
	private String parkingLot;
	private String carNumber;
	private String email;
	private LocalDate arrivalDate;
	private LocalDate leavingDate;
	private LocalTime arrivalHour;
	private LocalTime leavingHour;
	private String orderId;

	public OrderInAdvance(String Id, String parkinglot, String carNumber, String email, LocalDate arrivalDate,
			LocalDate leavingDate, LocalTime arrivalHour, LocalTime leavingHour) {

		this.customerId = Id;
		this.parkingLot = parkinglot;
		this.carNumber = carNumber;
		this.email = email;
		this.arrivalDate = arrivalDate;
		this.leavingDate = leavingDate;
		this.arrivalHour = arrivalHour;
		this.leavingHour = leavingHour;
		this.orderId = "Not yet initialized";
	}

	public String toString() {
		return "order ID: " + orderId + "\nCustomer ID: " + customerId + "\nParkinglot: " + parkingLot
				+ "\nCar Number: " + carNumber + "\nEmail: " + email + "\nArrival Date: " + arrivalDate
				+ "\nLeaving Date: " + leavingDate + "\nArrival Hour: " + arrivalHour + "\nLeaving Hour: " + leavingHour
				+ "\n";
	}

	public String getOrderId() {
		return orderId;
	}

	public String getParkinglot() {
		return parkingLot;
	}

	public String getCarNumber() {
		return carNumber;
	}

	public String getEmail() {
		return email;
	}

	public LocalDate getArrivalDate() {
		return arrivalDate;
	}

	public LocalDate getLeavingDate() {
		return leavingDate;
	}

	public LocalTime getArrivalHour() {
		return arrivalHour;
	}

	public LocalTime getLeavingHour() {
		return leavingHour;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
}
