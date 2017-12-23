package clientServerCPS;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import CPS_Utilities.CloseComplaintRequest;
import CPS_Utilities.LoginIdentification;
import entities.ChangeRatesRequest;
import entities.ChangeRatesResponse;
import entities.Complaint;
import entities.Customer;
import entities.Employee;
import entities.FullMembership;
import entities.Parkinglot;
import entities.PartialMembership;
import entities.Reservation;

public class RequestsSender
{
    private static String serverIP;
    
    public RequestsSender() throws IOException, URISyntaxException
    {
	BufferedReader bufferedReader = new BufferedReader(
		new InputStreamReader(getClass().getResourceAsStream("ServerIP.txt")));
	
	serverIP = bufferedReader.readLine();
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
}
