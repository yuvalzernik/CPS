package clientServerCPS;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import CPS_Utilities.CPS_Tracer;
import entities.Customer;
import entities.FullMembership;
import entities.PartialMembership;
import entities.Reservation;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;

public class Tests
{
    public static void main(String[] args)
    {
	try
	{
	    // FullMembershipTest() && PartialMembershipTest() && CustomerTest() && ReservationTest()
	    
	    if (ReservationTest())
	    {
		System.out.println("Test Succeed");
	    }
	    else
	    {
		System.out.println("Test Failed");
	    }
	}
	catch (Exception e)
	{
	    System.out.println("Failed with exception: " + e);
	}
    }
    
    private static boolean ReservationTest()
    {
	String id = Integer.toString(new Random().nextInt(1000000) + 3000000);
	
	Reservation reservation = new Reservation(ReservationType.InAdvance, id, "Testlot", "333333", LocalDate.now(),
		LocalDate.now(), LocalTime.parse("11:11"), LocalTime.parse("11:11"), ReservationStatus.NotStarted);
	
	ServerResponse<Reservation> serverResponse = RequestsSender.Reservation(reservation);
	
	CPS_Tracer.TraceInformation(serverResponse.toString());
	
	ServerResponse<Reservation> serverGetResponse = RequestsSender.GetReservation(serverResponse.GetResponseObject().getOrderId());
	
	CPS_Tracer.TraceInformation(serverResponse.toString());
	
	if (!serverGetResponse.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	return true;
    }
    
    private static boolean FullMembershipTest()
    {
	String id = Integer.toString(new Random().nextInt(1000000) + 3000000);
	
	FullMembership fullMembership = new FullMembership(id, LocalDate.now(), LocalDate.now(), "333333333");
	
	ServerResponse<FullMembership> serverResponse = RequestsSender.RegisterFullMembership(fullMembership);
	
	CPS_Tracer.TraceInformation(serverResponse.toString());
	
	ServerResponse<FullMembership> serverGetRespone = RequestsSender
		.GetFullMembership(serverResponse.GetResponseObject().GetSubscriptionId());
	
	CPS_Tracer.TraceInformation(serverGetRespone.toString());
	
	if (!serverGetRespone.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	return true;
    }
    
    private static boolean PartialMembershipTest()
    {
	String id = Integer.toString(new Random().nextInt(1000000) + 3000000);
	
	ArrayList<String> carList = new ArrayList<>();
	carList.add("444444444");
	carList.add("555555555");
	
	PartialMembership partialMembership = new PartialMembership(id, LocalDate.now(), LocalDate.now(), "testLot",
		carList, LocalTime.now());
	
	ServerResponse<PartialMembership> serverResponse = RequestsSender.RegisterPartialMembership(partialMembership);
	
	CPS_Tracer.TraceInformation(serverResponse.toString());
	
	ServerResponse<PartialMembership> serverGetRespone = RequestsSender
		.GetPartialMembership(serverResponse.GetResponseObject().GetSubscriptionId());
	
	CPS_Tracer.TraceInformation(serverGetRespone.toString());
	
	if (!serverGetRespone.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	return true;
    }
    
    private static boolean CustomerTest()
    {
	String id = Integer.toString(new Random().nextInt(1000000) + 3000000);
	
	Customer customer = new Customer(id, "test@gmail.com", 100);
	
	ServerResponse<Customer> serverResponse = RequestsSender.AddCustomerIfNotExists(customer);
	
	CPS_Tracer.TraceInformation(serverResponse.toString());
	
	ServerResponse<Customer> serverGetRespone = RequestsSender
		.GetCustomer(serverResponse.GetResponseObject().GetId());
	
	CPS_Tracer.TraceInformation(serverGetRespone.toString());
	
	if (!serverGetRespone.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	return true;
    }
}
