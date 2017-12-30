package CPS_Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.util.regex.Pattern;
import entities.Customer;
import entities.FullMembership;

public class InputValidator {
	public static boolean CarNumber(String carNumber) {
		if (!Pattern.matches("[0-9]+", carNumber) || carNumber.length() < 5 || carNumber.length() > 8) {
			return false;
		}
		return true;
	}

	public static boolean StartingDate(LocalDate startingDate) {
		if (startingDate.isBefore(LocalDate.now())) {
			return false;
		}
		return true;
	}

	public static boolean CheckLeavingDate(LocalDate startingDate, LocalDate leavingDate) {
		
		if (startingDate.isAfter(leavingDate))
			return false;
		if((Period.between(startingDate,leavingDate).getYears() > 0)||(Period.between(startingDate,leavingDate).getMonths()>0)||(Period.between(startingDate,leavingDate).getDays()>14)){	
			return false;
		}
		return true;
	}

	public static boolean Email(String email) {
		if (!Pattern.matches("^.+@.+\\..+$", email)) {
			return false;
		}
		return true;
	}

	public static boolean Id(String id) {
		if (!Pattern.matches("[0-9]+", id) || id.length() != 9) {
			return false;
		}
		return true;
	}

	public static boolean CheckHourFormat(String hour) {

		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); // HH = 24h
		// format
		dateFormat.setLenient(false); // this will not enable 25:67 for example
		try {
			System.out.println(dateFormat.parse(hour));
		} catch (ParseException e) {
			return false;
		}
		return true;

	}

	public static boolean CheckLeavingHour(String startingHour, String leavingHour, LocalDate startingDate,
			LocalDate leavingDate) {
		if (startingDate.equals(leavingDate) && (LocalTime.parse(startingHour).isAfter(LocalTime.parse(leavingHour))))
			return false;
		return true;

	}

	public static boolean FullMembership(FullMembership fullMembership) {
		if (CarNumber(fullMembership.GetCarNumber()) && StartingDate(fullMembership.GetStartDate())
				&& Id(fullMembership.GetCustomerId())) {
			return true;
		}
		return false;
	}

	public static boolean Customer(Customer customer) {
		if (Email(customer.GetEmail()) && Id(customer.GetId())) {
			return true;
		}
		return false;
	}

	public static boolean OrderInAdvance(String carNumber, LocalDate arrivalDate, LocalDate leavingDate,
			String arrivalHour, String leavingHour) {

		if (CarNumber(carNumber) && StartingDate(arrivalDate) && CheckHourFormat(arrivalHour)
				&& CheckHourFormat(leavingHour) && CheckLeavingHour(arrivalHour, leavingHour, arrivalDate, leavingDate)
				&& CheckLeavingDate(arrivalDate, leavingDate)) {
			return true;
		}
		return false;
	}

	public static boolean TextIsEmpty(String text) {
		if (text.length() > 0)
			return true;
		return false;

	}

	public static boolean OrderId(String orderid) {
		if (!Pattern.matches("[0-9]+", orderid))
			return false;
		return true;

	}

}
