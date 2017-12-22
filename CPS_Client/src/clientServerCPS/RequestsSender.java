package clientServerCPS;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import entities.Customer;
import entities.FullMembership;
import entities.Reservation;
import entities.PartialMembership;

public class RequestsSender
{
    private static String serverIP;
    
    public RequestsSender() throws IOException, URISyntaxException
    {
	serverIP = new String(Files.readAllBytes(Paths.get(getClass().getResource("ServerIP.txt").toURI())));
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
    
}
