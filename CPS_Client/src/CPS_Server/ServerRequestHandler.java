package CPS_Server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import clientServerCPS.ClientRequest;
import clientServerCPS.ClientRequestBase;
import clientServerCPS.ClientServerConsts;
import clientServerCPS.RequestResult;
import clientServerCPS.ServerResponse;
import entities.Customer;
import entities.FullMembership;

import java.util.concurrent.CompletableFuture;

import CPS_Utilities.CPS_Tracer;

public class ServerRequestHandler
{
    public void HandleRequestAsync(Socket socket)
    {
	CompletableFuture.runAsync(() ->
	{
	    try (Socket mySocket = socket;
		    ObjectInputStream inputStream = new ObjectInputStream(mySocket.getInputStream());
		    ObjectOutputStream outputStream = new ObjectOutputStream(mySocket.getOutputStream()))
	    {
		ClientRequestBase clientRequest = (ClientRequestBase) inputStream.readObject();
		
		outputStream.writeObject(ExtractAndApplyRequest(clientRequest));
	    }
	    catch (Exception e)
	    {
		CPS_Tracer.TraceError(e.toString());
	    }
	});
    }
    
    @SuppressWarnings("unchecked")
    private Object ExtractAndApplyRequest(ClientRequestBase clientRequestBase)
    {
	switch (clientRequestBase.getServerDestination())
	{
	case ClientServerConsts.RegisterFullMembership:
	    return RegisterFullMembership(((ClientRequest<FullMembership>) clientRequestBase).GetSentObject());
	
	case ClientServerConsts.AddCustomerIfNotExist:
	    return AddCustomerIfNotExist(((ClientRequest<Customer>) clientRequestBase).GetSentObject());
	
	default:
	    CPS_Tracer.TraceError(
		    "Server recived unknown destination. \nDestination: " + clientRequestBase.getServerDestination());
	    return null; // Todo - return server response somehow
	}
    }
    
    private ServerResponse<FullMembership> RegisterFullMembership(FullMembership fullMembership)
    {
	CPS_Tracer.TraceInformation("Registering full membership: \n" + fullMembership);
	
	// Todo - send to DB
	
	ServerResponse<FullMembership> serverResponse = new ServerResponse<>(RequestResult.Succeed, fullMembership,
		null);
	
	CPS_Tracer.TraceInformation("Server response to client after register: \n" + serverResponse);
	
	return serverResponse;
    }
    
    private ServerResponse<Customer> AddCustomerIfNotExist(Customer customer)
    {
	CPS_Tracer.TraceInformation("Trying to add customer: \n" + customer);
	
	// Todo - send to DB
	
	ServerResponse<Customer> serverResponse = new ServerResponse<>(RequestResult.Succeed, customer, null);
	
	CPS_Tracer.TraceInformation("Server response to client after trying to add customer: \n" + serverResponse);
	
	return serverResponse;
    }
}
