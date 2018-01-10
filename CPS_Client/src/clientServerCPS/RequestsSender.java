package clientServerCPS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import CPS_Utilities.Consts;
import CPS_Utilities.DialogBuilder;
import CPS_Utilities.LoginIdentification;
import entities.ActivityReport;
import entities.AddRealTimeParkingRequest;
import entities.ChangeParkingSpotStatusRequest;
import entities.ChangeParkinglotStatusRequest;
import entities.ChangeRatesRequest;
import entities.ChangeRatesResponse;
import entities.CloseComplaintRequest;
import entities.Complaint;
import entities.ComplaintsReport;
import entities.CreditCustomerRequest;
import entities.Customer;
import entities.DisabledReport;
import entities.Employee;
import entities.FullMembership;
import entities.ParkingSpot;
import entities.Parkinglot;
import entities.PartialMembership;
import entities.PerformanceReport;
import entities.RemoveCarRequest;
import entities.Reservation;
import entities.ReservationReport;
import entities.StatusReport;
import javafx.scene.control.Dialog;

public class RequestsSender
{
    private static String serverIP;
    
    public RequestsSender() throws IOException, URISyntaxException
    {
	ArrayList<String> ip = new ArrayList<>();
	
	ip.add("IP:");
	
	Dialog<List<String>> dialog = DialogBuilder.InputsDialog("Set server's IP", ip, Consts.Submit);
	
	dialog.setHeaderText("Add server ip or Cancel to use local host");
	
	Optional<List<String>> result = dialog.showAndWait();
	
	result.ifPresent(inputs ->
	{
	    serverIP = inputs.get(0);
	});
	
	if (!result.isPresent())
	{
	    serverIP = "127.0.0.1";
	}
    }
    
    public RequestsSender(String ip)
    {
	serverIP = ip;
    }
    
    @SuppressWarnings("unchecked")
    private static <T> ServerResponse<T> SendRequest(Object sentObject, String serverDestination)
    {
	try (Socket socket = new Socket(serverIP, ClientServerConsts.PORT);
		ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
		ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream()))
	{
	    
	    ClientRequest clientRequest = new ClientRequest(sentObject, serverDestination);
	    
	    outputStream.writeObject(clientRequest);
	    
	    ServerResponse<T> serverResponse = (ServerResponse<T>) inputStream.readObject();
	    
	    return serverResponse;
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	    return new ServerResponse<T>(RequestResult.Failed, null, "Internal server error");
	}
    }
    
    public static ServerResponse<FullMembership> RegisterFullMembership(FullMembership fullMembership)
    {
	return SendRequest(fullMembership, ClientServerConsts.RegisterFullMembership);
    }
    
    public static ServerResponse<PartialMembership> RegisterPartialMembership(PartialMembership partialMembership)
    {
	return SendRequest(partialMembership, ClientServerConsts.RegisterPartialMembership);
    }
    
    public static ServerResponse<Customer> AddCustomerIfNotExists(Customer customer)
    {
	return SendRequest(customer, ClientServerConsts.AddCustomerIfNotExists);
    }
    
    public static ServerResponse<FullMembership> GetFullMembership(String subscriptionId)
    {
	return SendRequest(subscriptionId, ClientServerConsts.GetFullMembership);
    }
    
    public static ServerResponse<PartialMembership> GetPartialMembership(String subscriptionId)
    {
	return SendRequest(subscriptionId, ClientServerConsts.GetPartialMembership);
    }
    
    public static ServerResponse<Customer> GetCustomer(String customerId)
    {
	return SendRequest(customerId, ClientServerConsts.GetCustomer);
    }
    
    public static ServerResponse<Reservation> Reservation(Reservation reservation)
    {
	return SendRequest(reservation, ClientServerConsts.Reservation);
    }
    
    public static ServerResponse<Reservation> GetReservation(String orderId)
    {
	return SendRequest(orderId, ClientServerConsts.GetReservation);
    }
    
    public static ServerResponse<Employee> GetEmployee(LoginIdentification loginIdentification)
    {
	return SendRequest(loginIdentification, ClientServerConsts.GetEmployee);
    }
    
    public static ServerResponse<Parkinglot> AddParkinglot(Parkinglot parkinglot)
    {
	return SendRequest(parkinglot, ClientServerConsts.AddParkinglot);
    }
    
    public static ServerResponse<ArrayList<Parkinglot>> GetAllParkinglots()
    {
	return SendRequest(null, ClientServerConsts.GetAllParkinglots);
    }
    
    public static ServerResponse<Parkinglot> GetParkinglot(String parkinglotName)
    {
	return SendRequest(parkinglotName, ClientServerConsts.GetParkingLot);
    }
    
    public static ServerResponse<ChangeParkinglotStatusRequest> ChangeParkinglotStatus(
	    ChangeParkinglotStatusRequest changeParkinglotStatusRequest)
    {
	return SendRequest(changeParkinglotStatusRequest, ClientServerConsts.ChangeParkinglotStatus);
    }
    
    public static ServerResponse<Complaint> AddComplaint(Complaint complaint)
    {
	return SendRequest(complaint, ClientServerConsts.AddComplaint);
    }
    
    public static ServerResponse<ArrayList<Complaint>> GetAllActiveComplaints()
    {
	return SendRequest(null, ClientServerConsts.GetAllActiveComplaints);
    }
    
    public static ServerResponse<CloseComplaintRequest> CloseComplaint(CloseComplaintRequest closeComplaintRequest)
    {
	return SendRequest(closeComplaintRequest, ClientServerConsts.CloseComplaint);
    }
    
    public static ServerResponse<ChangeRatesRequest> AddChangeRatesRequest(ChangeRatesRequest changeRatesRequest)
    {
	return SendRequest(changeRatesRequest, ClientServerConsts.AddChangeRatesRequest);
    }
    
    public static ServerResponse<ChangeRatesResponse> CloseChangeRatesRequest(ChangeRatesResponse changeRatesResponse)
    {
	return SendRequest(changeRatesResponse, ClientServerConsts.CloseChangeRatesRequest);
    }
    
    public static ServerResponse<ArrayList<ChangeRatesRequest>> GetAllChangeRatesRequests()
    {
	return SendRequest(null, ClientServerConsts.GetAllChangeRatesRequests);
    }
    
    public static ServerResponse<ChangeParkingSpotStatusRequest> ChangeParkingSpotStatus(
	    ChangeParkingSpotStatusRequest changeParkingSpotStatusRequest)
    {
	return SendRequest(changeParkingSpotStatusRequest, ClientServerConsts.ChangeParkingSpotStatus);
    }
    
    public static ServerResponse<ArrayList<ParkingSpot>> GetAllDisabledParkingSpots()
    {
	return SendRequest(null, ClientServerConsts.GetAllDisabledParkingSpots);
    }

    public static ServerResponse<FullMembership> ChangeExpireFullMembership(FullMembership fullMembership)
    {
	return SendRequest(fullMembership, ClientServerConsts.ChangeExpireFullMembership);
    }
    
    public static ServerResponse<PartialMembership> ChangeExpirePartialMembership(PartialMembership partialMembership)
    {
	return SendRequest(partialMembership, ClientServerConsts.ChangeExpirePartialMembership);
    }
    
    public static ServerResponse<AddRealTimeParkingRequest> TryInsertCar(AddRealTimeParkingRequest addRealTimeParkingRequest)
    {
    return SendRequest(addRealTimeParkingRequest, ClientServerConsts.TryInsertGuestCar);
    }
    public static ServerResponse<RemoveCarRequest> RemoveCar(RemoveCarRequest removeCarRequest)
    {
	return SendRequest(removeCarRequest, ClientServerConsts.RemoveCar);
    }
    
    public static ServerResponse<ComplaintsReport> GetComplaintsReport()
    {
	return SendRequest(null, ClientServerConsts.GetComplaintsReport);
    }
    
    public static ServerResponse<PerformanceReport> GetPerformanceReport()
    {
	return SendRequest(null, ClientServerConsts.GetPerformanceReport);
    }
    
    public static ServerResponse<ReservationReport> GetReservationReport(String parkinglotName)
    {
	return SendRequest(parkinglotName, ClientServerConsts.GetReservationReport);
    }
    
    public static ServerResponse<DisabledReport> GetDisabledReport(String parkinglotName)
    {
	return SendRequest(parkinglotName, ClientServerConsts.GetDisabledReport);
    }
    
    public static ServerResponse<ActivityReport> GetActivityReport(LocalDate localDate)
    {
	return SendRequest(localDate, ClientServerConsts.GetActivityReport);
    }
    
    public static ServerResponse<StatusReport> GetStatusReport()
    {
	return SendRequest(null, ClientServerConsts.GetStatusReport);
    }
    
    public static ServerResponse<String> LoginUser(String username)
    {
	return SendRequest(username, ClientServerConsts.LoginUser);
    }
    
    public static ServerResponse<String> LogoutUser(String username)
    {
	return SendRequest(username, ClientServerConsts.LogoutUser);
    }
    
    public static ServerResponse<String> CloseReservation(String reservationId)
    {
	return SendRequest(reservationId, ClientServerConsts.CloseReservation);
    }
    
    public static ServerResponse<CreditCustomerRequest> CreditCustomer(CreditCustomerRequest request)
    {
	return SendRequest(request, ClientServerConsts.AddCreditToCustomer);
    }
}
