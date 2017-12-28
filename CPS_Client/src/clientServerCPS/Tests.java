package clientServerCPS;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Function;

import CPS_Utilities.CPS_Tracer;
import CPS_Utilities.LoginIdentification;
import entities.AddRealTimeParkingRequest;
import entities.ChangeParkingSpotStatusRequest;
import entities.ChangeParkinglotStatusRequest;
import entities.ChangeRatesRequest;
import entities.ChangeRatesResponse;
import entities.CloseComplaintRequest;
import entities.Complaint;
import entities.Customer;
import entities.Employee;
import entities.FullMembership;
import entities.ParkingSpot;
import entities.Parkinglot;
import entities.PartialMembership;
import entities.Reservation;
import entities.enums.ParkingSpotStatus;
import entities.enums.ParkinglotStatus;
import entities.enums.ReservationStatus;
import entities.enums.ReservationType;

@SuppressWarnings("unused")
public class Tests
{
    public static void main(String[] args)
    {
	try
	{
	    new RequestsSender("127.0.0.1");
	    
	    // FullMembershipTest() PartialMembershipTest() ComplaintTest()
	    // CustomerTest() ReservationTest() ChangeRatesTest()
	    // EmployeeTest() ParkinglotTest() DisabledParkingSpotsTest()
	    // GuestEntryTest()
	    for (int i = 0; i < 1; i++)
	    {
		if (GuestEntryTest())
		{
		    System.out.println("Test Succeed");
		}
		else
		{
		    System.out.println("Test Failed");
		}
	    }
	    
	}
	catch (Exception e)
	{
	    System.out.println("Failed with exception: " + e);
	    e.printStackTrace();
	}
    }
    
    private static boolean GuestEntryTest()
    {
	ServerResponse<AddRealTimeParkingRequest> serverResponse = null;
	
	for (int i = 0; i < 1; i++)
	{
	    AddRealTimeParkingRequest request = new AddRealTimeParkingRequest("Test lot", LocalDateTime.now(),
		    LocalDateTime.now().plusHours(5), "3333333" + i);
	    
	    serverResponse = RequestsSender.TryInsertGuestCar(request);
	    
	    CPS_Tracer.TraceInformation("server respnse after trying to add car: \n" + serverResponse);
	}
	
	if (!serverResponse.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	return true;
    }
    
    private static boolean DisabledParkingSpotsTest()
    {
	ChangeParkingSpotStatusRequest request = new ChangeParkingSpotStatusRequest(new ParkingSpot(1, 1, 2),
		"Test lot", ParkingSpotStatus.Disabled);
	
	ServerResponse<ChangeParkingSpotStatusRequest> serverResponse = RequestsSender.ChangeParkingSpotStatus(request);
	
	if (!serverResponse.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	ServerResponse<ArrayList<ParkingSpot>> serverResponse2 = RequestsSender.GetAllDisabledParkingSpots();
	
	CPS_Tracer.TraceInformation(serverResponse2.toString());
	
	if (!serverResponse2.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	request = new ChangeParkingSpotStatusRequest(new ParkingSpot(1, 1, 2), "Test lot", ParkingSpotStatus.Active);
	
	ServerResponse<ChangeParkingSpotStatusRequest> serverResponse3 = RequestsSender
		.ChangeParkingSpotStatus(request);
	
	if (!serverResponse3.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	return true;
    }
    
    private static boolean ChangeRatesTest()
    {
	ChangeRatesRequest changeRatesRequest = new ChangeRatesRequest("Test lot", 55, 55);
	
	ServerResponse<ChangeRatesRequest> serverResponse = RequestsSender.AddChangeRatesRequest(changeRatesRequest);
	
	ServerResponse<ArrayList<ChangeRatesRequest>> serverListResponse = RequestsSender.GetAllChangeRatesRequests();
	
	System.out.println(serverListResponse.GetResponseObject());
	
	ChangeRatesResponse changeRatesResponse = new ChangeRatesResponse(
		serverResponse.GetResponseObject().getRequestId(), true);
	
	ServerResponse<ChangeRatesResponse> serverChangeResponse = RequestsSender
		.CloseChangeRatesRequest(changeRatesResponse);
	
	if (!serverChangeResponse.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	return true;
    }
    
    private static boolean ComplaintTest()
    {
	Complaint complaint = new Complaint("301731469",
		"My Ferari got dirty with dust in Nosh parkinglot.. \nI will not bring my Lamburgini unless u compensate me in 1,000$ !!!");
	
	ServerResponse<Complaint> serverResponse = RequestsSender.AddComplaint(complaint);
	
	ServerResponse<ArrayList<Complaint>> serverGetResponse = RequestsSender.GetAllActiveComplaints();
	
	CPS_Tracer.TraceInformation(serverGetResponse.toString());
	;
	
	boolean isMyComplaintThere = false;
	
	for (Complaint c : serverGetResponse.GetResponseObject())
	{
	    if (c.getComplaintId().equals(serverResponse.GetResponseObject().getComplaintId()))
	    {
		isMyComplaintThere = true;
	    }
	}
	
	if (!isMyComplaintThere)
	{
	    return false;
	}
	
	CloseComplaintRequest closeComplaintRequest = new CloseComplaintRequest(
		serverResponse.GetResponseObject().getComplaintId(), 2000);
	
	ServerResponse<CloseComplaintRequest> serverResponse2 = RequestsSender.CloseComplaint(closeComplaintRequest);
	
	if (!serverResponse2.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	return true;
    }
    
    private static boolean ParkinglotTest()
    {
	String seed = Integer.toString(new Random().nextInt(1000000) + 3000000);
	
	Parkinglot parkinglot = new Parkinglot("Test lot" + seed, 5, ParkinglotStatus.Closed, 10, 20);
	
	ServerResponse<Parkinglot> serverResponse = RequestsSender.AddParkinglot(parkinglot);
	
	CPS_Tracer.TraceInformation(serverResponse.toString());
	
	ServerResponse<Parkinglot> serverGetResponse = RequestsSender.GetParkinglot("Test lot" + seed);
	
	ServerResponse<ChangeParkinglotStatusRequest> serverResponse2 = RequestsSender.ChangeParkinglotStatus(
		new ChangeParkinglotStatusRequest(serverGetResponse.GetResponseObject().getParkinglotName(),
			ParkinglotStatus.Closed));
	
	ServerResponse<ArrayList<Parkinglot>> serverGetAllResponse = RequestsSender.GetAllParkinglots();
	
	CPS_Tracer.TraceInformation(serverGetAllResponse.toString());
	
	serverGetResponse = RequestsSender.GetParkinglot("Testlot" + seed);
	
	if (!serverResponse2.GetRequestResult().equals(RequestResult.Succeed)
		|| !serverGetResponse.GetResponseObject().getStatus().equals(ParkinglotStatus.Closed))
	{
	    return false;
	}
	
	return true;
    }
    
    private static boolean EmployeeTest()
    {
	LoginIdentification creds = new LoginIdentification("benalfasi", "notrealpw");
	
	ServerResponse<Employee> serverResponse = RequestsSender.GetEmployee(creds);
	
	CPS_Tracer.TraceInformation(serverResponse.toString());
	
	if (!serverResponse.GetRequestResult().equals(RequestResult.Succeed))
	{
	    return false;
	}
	
	LoginIdentification falseCreds = new LoginIdentification("blabla", "1234");
	
	ServerResponse<Employee> serverResponse2 = RequestsSender.GetEmployee(falseCreds);
	
	CPS_Tracer.TraceInformation(serverResponse2.toString());
	
	if (!serverResponse2.GetRequestResult().equals(RequestResult.WrongCredentials))
	{
	    return false;
	}
	
	return true;
    }
    
    private static boolean ReservationTest()
    {
	String id = Integer.toString(new Random().nextInt(1000000) + 3000000);
	
	Reservation reservation = new Reservation(ReservationType.Web, id, "Testlot", "333333", LocalDate.now(),
		LocalDate.now(), LocalTime.parse("11:11"), LocalTime.parse("11:11"), ReservationStatus.NotStarted);
	
	ServerResponse<Reservation> serverResponse = RequestsSender.Reservation(reservation);
	
	CPS_Tracer.TraceInformation(serverResponse.toString());
	
	ServerResponse<Reservation> serverGetResponse = RequestsSender
		.GetReservation(serverResponse.GetResponseObject().getOrderId());
	
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
	
	PartialMembership partialMembership = new PartialMembership(id, LocalDate.now(), LocalDate.now().plusDays(20),
		"Test lot", carList, LocalTime.now());
	
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
