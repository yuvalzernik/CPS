package CPS_Utilities;

import java.time.LocalDate;
import java.util.regex.Pattern;

import application.Models.FullMembership;
import application.Models.Customer;;

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
    
    public static boolean StartingDate(LocalDate startingDate)
    {
	if (startingDate.isBefore(LocalDate.now()))
	{
	    System.out.println("3");
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
    
    public static boolean FullMembership(FullMembership fullMembership)
    {
	if (CarNumber(fullMembership.GetCarNumber()) && StartingDate(fullMembership.GetStartingDate())
		&& Id(fullMembership.GetId()))
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
}
