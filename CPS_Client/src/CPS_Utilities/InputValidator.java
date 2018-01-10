package CPS_Utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;
import entities.Customer;
import entities.FullMembership;

public class InputValidator
{
    public static boolean CarNumber(String carNumber)
    {
	if (!Pattern.matches("[0-9]+", carNumber) || carNumber.length() < 5 || carNumber.length() > 8)
	{
	    return false;
	}
	return true;
    }
    
    public static boolean CreditCardNumber(String creditcard)
    {
	if (!Pattern.matches("[0-9]+", creditcard))
	{
	    return false;
	}
	return true;
    }
    
    public static boolean Ccv(String ccv)
    {
	if ((!Pattern.matches("[0-9]+", ccv)) || (ccv.length() != 3)) return false;
	return true;
    }
    
    public static boolean ExpirationDate(YearMonth date)
    {
	if (date.isBefore(YearMonth.now()))
	{
	    return false;
	}
	return true;
    }
    
    public static boolean StartingDate(LocalDate startingDate)
    {
	if (startingDate.isBefore(LocalDate.now()))
	{
	    return false;
	}
	return true;
    }
    
    public static boolean CheckLeavingDate(LocalDate startingDate, LocalDate leavingDate)
    {
	
	if (startingDate.isAfter(leavingDate)) return false;
	if ((Period.between(startingDate, leavingDate).getYears() > 0)
		|| (Period.between(startingDate, leavingDate).getMonths() > 0)
		|| (Period.between(startingDate, leavingDate).getDays() > 14))
	{
	    return false;
	}
	return true;
    }
    
    public static boolean Email(String email)
    {
	if (!Pattern.matches("^.+@.+\\..+$", email))
	{
	    return false;
	}
	return true;
    }
    
    public static boolean Id(String id)
    {
	if (!Pattern.matches("[0-9]+", id) || id.length() != 9)
	{
	    return false;
	}
	return true;
	
    }
    
    public static boolean CheckVisaDate(String date)
    {
	try
	{
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
	    return ExpirationDate(YearMonth.parse(date, formatter));
	}
	catch (Exception e)
	{
	    return false;
	}
    }
    
    public static boolean CheckHourFormat(String hour)
    {
	
	SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm"); // HH = 24h
	// format
	dateFormat.setLenient(false); // this will not enable 25:67 for example
	try
	{
	    dateFormat.parse(hour);
	}
	catch (ParseException e)
	{
	    return false;
	}
	return true;
    }
    
    public static boolean CheckLeavingHour(String startingHour, String leavingHour, LocalDate startingDate,
	    LocalDate leavingDate)
    {
	if (startingDate.equals(leavingDate) && (LocalTime.parse(startingHour).isAfter(LocalTime.parse(leavingHour))))
	    return false;
	return true;
    }
    
    public static boolean FullMembership(FullMembership fullMembership)
    {
	if (CarNumber(fullMembership.GetCarNumber()) && StartingDate(fullMembership.GetStartDate())
		&& Id(fullMembership.GetCustomerId()))
	{
	    return true;
	}
	return false;
    }
    
    public static boolean Customer(Customer customer)
    {
	if (Email(customer.GetEmail()) && Id(customer.GetId()))
	{
	    return true;
	}
	return false;
    }
    
    public static boolean OrderInAdvance(String carNumber, LocalDate arrivalDate, LocalDate leavingDate,
	    String arrivalHour, String leavingHour)
    {
	
	if (CarNumber(carNumber) && StartingDate(arrivalDate) && CheckHourFormat(arrivalHour)
		&& CheckHourFormat(leavingHour) && CheckLeavingHour(arrivalHour, leavingHour, arrivalDate, leavingDate)
		&& CheckLeavingDate(arrivalDate, leavingDate))
	{
	    return true;
	}
	return false;
    }
    
    public static boolean PartialMembership(ArrayList<String> carlist, String email, LocalDate arrivalDate)
    {
	
	if (CheckCarList(carlist) && StartingDate(arrivalDate) && Email(email))
	{
	    return true;
	}
	return false;
    }
    
    public static boolean CheckCarList(ArrayList<String> carlist)
    {
	if (carlist.isEmpty()) return false;
	for (String string : carlist)
	{
	    if (!CarNumber(string)) return false;
	}
	return true;
	
    }
    
    public static boolean TextIsEmpty(String text)
    {
	if (text.length() > 0) return true;
	return false;
	
    }
    
    public static boolean OrderId(String orderid)
    {
	if (!Pattern.matches("[0-9]+", orderid)) return false;
	return true;
	
    }
    
}
